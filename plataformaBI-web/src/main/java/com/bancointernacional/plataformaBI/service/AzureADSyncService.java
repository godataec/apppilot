package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.domain.dto.azure.*;
import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AzureADSyncService {

    private static final Logger logger = LogManager.getLogger(AzureADSyncService.class);
    private static final String GRAPH_API_BASE_URL = "https://graph.microsoft.com/v1.0";

    @Value("${azure.ad.client.id}")
    private String clientId;

    @Value("${azure.ad.client.secret:}")
    private String clientSecret;

    @Value("${azure.ad.tenant.id}")
    private String tenantId;

    @Autowired
    private UserService userService;

    private final RestTemplate restTemplate;

    public AzureADSyncService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Acquires an access token from Azure AD using client credentials flow
     */
    private String acquireAccessToken() {
        // Check if client secret is configured
        if (clientSecret == null || clientSecret.trim().isEmpty()) {
            throw new IllegalStateException(
                "Azure AD client secret is not configured. " +
                "Please set 'azure.ad.client.secret' in application.properties to use the user synchronization feature."
            );
        }

        try {
            String tokenEndpoint = "https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("client_id", clientId);
            requestBody.add("client_secret", clientSecret);
            requestBody.add("scope", "https://graph.microsoft.com/.default");
            requestBody.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                tokenEndpoint,
                request,
                JsonNode.class
            );

            if (response.getBody() != null && response.getBody().has("access_token")) {
                String accessToken = response.getBody().get("access_token").asText();
                logger.info("Successfully acquired access token from Azure AD");
                return accessToken;
            } else {
                throw new RuntimeException("Failed to acquire access token: No token in response");
            }

        } catch (HttpClientErrorException e) {
            logger.error("Error acquiring access token: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to acquire access token from Azure AD: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error acquiring access token: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to acquire access token from Azure AD: " + e.getMessage(), e);
        }
    }

    /**
     * Synchronizes users from Azure AD with proper error handling and token management
     * Refactored to use RestTemplate instead of WebClient
     */
    public List<UserDTO> synchronizeUsers(String accessToken) {
        logger.info("Starting Azure AD user synchronization with client ID: {}", clientId);
        List<UserDTO> synchronizedUsers = new ArrayList<>();

        try {
            // If no token provided, acquire one automatically
            if (accessToken == null || accessToken.trim().isEmpty()) {
                logger.info("No access token provided, acquiring new token from Azure AD");
                accessToken = acquireAccessToken();
            }

            // Validate token format
            if (!accessToken.startsWith("eyJ")) {
                throw new IllegalArgumentException("Invalid access token format. Token should be a JWT starting with 'eyJ'");
            }

            logger.debug("Access token validated. Proceeding with synchronization...");

            // Get app groups using RestTemplate
            AzureResponseDTO<AzureGroupDTO> groupsResponse = getAppGroups(accessToken);

            if (groupsResponse != null && groupsResponse.getValue() != null) {
                logger.info("Found {} groups for synchronization", groupsResponse.getValue().size());

                for (AzureGroupDTO group : groupsResponse.getValue()) {
                    try {
                        logger.debug("Processing group with principal ID: {}", group.getPrincipalId());

                        // Get users by group
                        AzureResponseDTO<AzureUserDTO> usersResponse = getAppUsersByGroup(group.getPrincipalId(), accessToken);

                        // Get role name for this group
                        String role = getGroupRoleName(group.getPrincipalId(), accessToken);

                        if (usersResponse != null && usersResponse.getValue() != null) {
                            logger.debug("Found {} users in group {}", usersResponse.getValue().size(), group.getPrincipalId());

                            for (AzureUserDTO azureUser : usersResponse.getValue()) {
                                try {
                                    UserDTO newUser = UserDTO.builder()
                                            .userId(UUID.randomUUID())
                                            .openIdUser(azureUser.getId())
                                            .name(azureUser.getGivenName())
                                            .lastName(azureUser.getSurname())
                                            .status("true")
                                            .role(role != null ? role.toUpperCase() : "USER")
                                            .email(azureUser.getMail())
                                            .build();

                                    // Use verifyAndSaveUser to handle existing users properly
                                    Optional<UserDTO> savedUserOpt = userService.verifyAndSaveUser(newUser);
                                    if (savedUserOpt.isPresent()) {
                                        synchronizedUsers.add(savedUserOpt.get());
                                        logger.debug("Successfully synchronized user: {} {}", azureUser.getGivenName(), azureUser.getSurname());
                                    } else {
                                        logger.warn("Failed to save user: {} {}", azureUser.getGivenName(), azureUser.getSurname());
                                    }
                                } catch (Exception e) {
                                    logger.error("Error processing user {}: {}", azureUser.getId(), e.getMessage());
                                }
                            }
                        } else {
                            logger.warn("No users found for group: {}", group.getPrincipalId());
                        }
                    } catch (Exception e) {
                        logger.error("Error processing group {}: {}", group.getPrincipalId(), e.getMessage());
                        // Continue with next group instead of failing completely
                    }
                }
            } else {
                logger.warn("No groups found for client ID: {}", clientId);
            }
        } catch (HttpClientErrorException e) {
            logger.error("Microsoft Graph API error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());

            // Enhanced error handling for common authentication issues
            if (e.getStatusCode().value() == 401) {
                String errorBody = e.getResponseBodyAsString();
                if (errorBody.contains("InvalidAuthenticationToken") && errorBody.contains("Invalid audience")) {
                    throw new RuntimeException("Authentication Error: The access token has an invalid audience. " +
                            "Please ensure the token is issued for Microsoft Graph API (audience: https://graph.microsoft.com) " +
                            "and not for your application API. The token should be obtained with scope 'https://graph.microsoft.com/.default' " +
                            "or specific Graph API scopes like 'User.Read.All Group.Read.All'.", e);
                } else if (errorBody.contains("InvalidAuthenticationToken")) {
                    throw new RuntimeException("Authentication Error: Invalid or expired access token. " +
                            "Please obtain a fresh token with the required Microsoft Graph API permissions: " +
                            "User.Read.All, Group.Read.All, GroupMember.Read.All, AppRoleAssignment.Read.All", e);
                }
            }

            throw new RuntimeException("Microsoft Graph API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            logger.error("Error during Azure AD synchronization", e);
            throw new RuntimeException("Failed to synchronize users from Azure AD: " + e.getMessage(), e);
        }

        logger.info("Azure AD synchronization completed. Successfully synchronized {} users", synchronizedUsers.size());
        return synchronizedUsers;
    }

    /**
     * Gets users by group ID using RestTemplate
     */
    private AzureResponseDTO<AzureUserDTO> getAppUsersByGroup(String resourceId, String accessToken) {
        String url = GRAPH_API_BASE_URL + "/groups/" + resourceId + "/members";
        return get(url, accessToken, new ParameterizedTypeReference<AzureResponseDTO<AzureUserDTO>>() {});
    }

    /**
     * Gets app groups using RestTemplate
     */
    private AzureResponseDTO<AzureGroupDTO> getAppGroups(String accessToken) {
        AzureResponseDTO<AzureGroupDTO> principalResponse = getAppPrincipalId(clientId, accessToken);

        if (principalResponse.getValue() != null && !principalResponse.getValue().isEmpty()) {
            String principalId = principalResponse.getValue().get(0).getId();
            String url = GRAPH_API_BASE_URL + "/servicePrincipals/" + principalId + "/appRoleAssignedTo";

            return get(url, accessToken, new ParameterizedTypeReference<AzureResponseDTO<AzureGroupDTO>>() {});
        }

        return new AzureResponseDTO<AzureGroupDTO>();
    }

    /**
     * Gets app principal ID using RestTemplate
     */
    private AzureResponseDTO<AzureGroupDTO> getAppPrincipalId(String clientId, String accessToken) {
        String url = GRAPH_API_BASE_URL + "/servicePrincipals?$filter=appId eq '" + clientId + "'";
        return get(url, accessToken, new ParameterizedTypeReference<AzureResponseDTO<AzureGroupDTO>>() {});
    }

    /**
     * Gets group role name using RestTemplate
     */
    private String getGroupRoleName(String groupId, String accessToken) {
        try {
            String assignmentUrl = GRAPH_API_BASE_URL + "/groups/" + groupId + "/appRoleAssignments?$select=appRoleId,resourceId";

            AzureResponseDTO<AzureRoleAssignmentDTO> assignmentResponse = get(assignmentUrl, accessToken,
                new ParameterizedTypeReference<AzureResponseDTO<AzureRoleAssignmentDTO>>() {});

            if (assignmentResponse.getValue() != null && !assignmentResponse.getValue().isEmpty()) {
                AzureRoleAssignmentDTO assignment = assignmentResponse.getValue().get(0);
                String spUrl = GRAPH_API_BASE_URL + "/servicePrincipals/" + assignment.getResourceId() + "?$select=appRoles";

                AzureServicePrincipalDTO spResponse = get(spUrl, accessToken,
                    new ParameterizedTypeReference<AzureServicePrincipalDTO>() {});

                if (spResponse.getAppRoles() != null) {
                    return spResponse.getAppRoles().stream()
                            .filter(role -> assignment.getAppRoleId().equals(role.getId()))
                            .map(AzureAppRoleDTO::getValue)
                            .findFirst()
                            .orElse("USER");
                }
                return "USER";
            }

            logger.error("No app-role assignment found for group {}", groupId);
            return "USER";
        } catch (Exception e) {
            logger.error("Error getting role name for group {}: {}", groupId, e.getMessage());
            return "USER";
        }
    }

    /**
     * Generic GET method using RestTemplate with proper error handling
     */
    private <T> T get(String url, String accessToken, ParameterizedTypeReference<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                responseType
            );

            logger.debug("Successfully called Graph API URL: {}", url);
            return response.getBody();

        } catch (HttpClientErrorException e) {
            logger.error("Graph API error for URL {}: {} - {}", url, e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            logger.error("Error calling Graph API URL {}: {}", url, e.getMessage());
            throw new RuntimeException("Failed to call Microsoft Graph API", e);
        }
    }
}
