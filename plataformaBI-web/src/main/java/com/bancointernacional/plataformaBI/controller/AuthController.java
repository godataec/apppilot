package com.bancointernacional.plataformaBI.controller;

import com.bancointernacional.plataformaBI.auth.JwtTokenUtil;
import com.bancointernacional.plataformaBI.domain.dto.auth.TokenResponse;
import com.bancointernacional.plataformaBI.domain.dto.auth.UserInfoDTO;
import com.bancointernacional.plataformaBI.service.JwtGeneratorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Authentication Controller
 * Handles authentication endpoints for the frontend application
 * Including JWT token generation from SAML session
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:4200", "https://localhost:4200"}, allowCredentials = "true")
public class AuthController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private JwtGeneratorService jwtGeneratorService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${frontend.login.url}")
    private String frontendLoginUrl;

    /**
     * Generate JWT token from SAML session
     * This endpoint is called by the frontend after successful SAML authentication
     *
     * @param session HTTP session containing SAML authentication
     * @return JWT token and user information
     */
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateToken(HttpSession session) {
        try {
            logger.info("Token generation requested");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                logger.error("No authenticated user found in session");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(TokenResponse.error("No authenticated session found"));
            }

            // Check if this is a SAML authentication
            if (!(authentication instanceof Saml2Authentication)) {
                logger.error("Authentication is not SAML based");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(TokenResponse.error("Invalid authentication type"));
            }

            Saml2Authentication saml2Auth = (Saml2Authentication) authentication;
            Saml2AuthenticatedPrincipal principal = (Saml2AuthenticatedPrincipal) saml2Auth.getPrincipal();

            // Extract user information from SAML assertion
            String email = extractEmail(principal);
            String name = extractName(principal);
            List<String> roles = extractRoles(authentication);

            logger.info("Generating JWT for user: {} with roles: {}", email, roles);

            // Generate JWT token
            String token = jwtGeneratorService.generateToken(email, name, roles);

            // Create user info DTO
            UserInfoDTO userInfo = UserInfoDTO.builder()
                    .email(email)
                    .name(name)
                    .roles(roles)
                    .objectId(extractAttributeAsString(principal, "http://schemas.microsoft.com/identity/claims/objectidentifier"))
                    .build();

            // Return token response
            TokenResponse response = TokenResponse.success(token, userInfo);
            logger.info("JWT token generated successfully for user: {}", email);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error generating JWT token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(TokenResponse.error("Error generating token: " + e.getMessage()));
        }
    }

    /**
     * Validate JWT token
     * Checks if the provided JWT token is valid
     *
     * @return validation result
     */
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken() {
        try {
            Map<String, Object> response = new HashMap<>();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null || !auth.isAuthenticated()) {
                response.put("valid", false);
                response.put("message", "No authenticated user");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            response.put("valid", true);
            response.put("user", jwtTokenUtil.getCurrentUserEmail());
            response.put("roles", jwtTokenUtil.getCurrentUserRoles());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error validating token", e);
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("message", "Error validating token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get current user information from JWT token
     *
     * @return current user information
     */
    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getCurrentUser() {
        try {
            String email = jwtTokenUtil.getCurrentUserEmail();
            String name = jwtTokenUtil.getCurrentUserName();
            List<String> roles = jwtTokenUtil.getCurrentUserRoles();
            String objectId = jwtTokenUtil.getCurrentUserObjectId();
            String tenantId = jwtTokenUtil.getTenantId();

            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            UserInfoDTO userInfo = UserInfoDTO.builder()
                    .email(email)
                    .name(name)
                    .roles(roles)
                    .objectId(objectId)
                    .tenantId(tenantId)
                    .build();

            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            logger.error("Error getting current user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Check authentication status
     *
     * @return authentication status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus() {
        Map<String, Object> response = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        response.put("authenticated", auth != null && auth.isAuthenticated());

        if (auth != null && auth.isAuthenticated()) {
            response.put("user", jwtTokenUtil.getCurrentUserEmail());
            response.put("name", jwtTokenUtil.getCurrentUserName());
            response.put("roles", jwtTokenUtil.getCurrentUserRoles());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Initiate logout
     * Returns the SAML logout URL for the frontend to redirect to
     *
     * @return logout information
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request) {
        logger.info("=== Logout API Called ===");
        logger.info("Request Method: {}", request.getMethod());
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Origin: {}", request.getHeader("Origin"));
        logger.info("Content-Type: {}", request.getHeader("Content-Type"));
        logger.info("Authorization Header Present: {}", request.getHeader("Authorization") != null);

        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info("Session found: {}", session.getId());
            logger.info("Session creation time: {}", new java.util.Date(session.getCreationTime()));
            logger.info("Session last accessed: {}", new java.util.Date(session.getLastAccessedTime()));
        } else {
            logger.warn("No session found in logout request");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info("Authentication type: {}", auth.getClass().getSimpleName());
            logger.info("Is authenticated: {}", auth.isAuthenticated());
            logger.info("Principal: {}", auth.getName());
        } else {
            logger.warn("No authentication found in SecurityContext");
        }

        Map<String, Object> response = new HashMap<>();

        try {
            logger.info("Frontend login URL from properties: {}", frontendLoginUrl);
            String encodedReturnTo = URLEncoder.encode(frontendLoginUrl, "UTF-8");
            String logoutUrl = "/saml/logout?returnTo=" + encodedReturnTo;
            String fullLogoutUrl = "http://localhost:8080" + logoutUrl;

            response.put("success", true);
            response.put("logoutUrl", logoutUrl);
            response.put("fullLogoutUrl", fullLogoutUrl);
            response.put("message", "Redirect to SAML logout");

            logger.info("=== Logout Response Prepared ===");
            logger.info("Logout URL (relative): {}", logoutUrl);
            logger.info("Logout URL (full): {}", fullLogoutUrl);
            logger.info("Encoded returnTo: {}", encodedReturnTo);
            logger.info("Response JSON: {}", response);
            logger.info("Frontend should redirect to: {}", fullLogoutUrl);
        } catch (UnsupportedEncodingException e) {
            logger.error("Error encoding return URL", e);
            response.put("success", false);
            response.put("message", "Error preparing logout URL");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        logger.info("=== Returning logout response with HTTP 200 ===");
        return ResponseEntity.ok(response);
    }




    // Helper methods to extract information from SAML principal

    private String extractEmail(Saml2AuthenticatedPrincipal principal) {
        // Try different possible email attributes
        String email = extractAttributeAsString(principal, "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress");
        if (email == null) {
            email = extractAttributeAsString(principal, "email");
        }
        if (email == null) {
            email = extractAttributeAsString(principal, "preferred_username");
        }
        if (email == null) {
            email = principal.getName();
        }
        return email;
    }

    private String extractName(Saml2AuthenticatedPrincipal principal) {
        // Try different possible name attributes
        String name = extractAttributeAsString(principal, "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name");
        if (name == null) {
            name = extractAttributeAsString(principal, "name");
        }
        if (name == null) {
            name = extractAttributeAsString(principal, "displayname");
        }
        return name != null ? name : "Unknown User";
    }

    private List<String> extractRoles(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring(5)) // Remove "ROLE_" prefix
                .collect(Collectors.toList());
    }

    private String extractAttributeAsString(Saml2AuthenticatedPrincipal principal, String attributeName) {
        List<Object> attributes = principal.getAttribute(attributeName);
        if (attributes != null && !attributes.isEmpty()) {
            Object firstAttribute = attributes.get(0);
            return firstAttribute != null ? firstAttribute.toString() : null;
        }
        return null;
    }
}

