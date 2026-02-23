package com.bancointernacional.plataformaBI.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    /**
     * Get the current authenticated user's JWT token (OAuth2 JWT)
     */
    public Jwt getCurrentUserToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getToken();
        }
        return null;
    }

    /**
     * Get the current user's email/username from authentication
     */
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        // For OAuth2 JWT tokens
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt token = ((JwtAuthenticationToken) authentication).getToken();
            String email = token.getClaimAsString("preferred_username");
            if (email != null) {
                return email;
            }
            return token.getClaimAsString("email");
        }

        // For custom JWT tokens (UsernamePasswordAuthenticationToken)
        if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }

        return authentication.getName();
    }

    /**
     * Get the current user's name from the JWT token
     */
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt token = ((JwtAuthenticationToken) authentication).getToken();
            return token.getClaimAsString("name");
        }
        // For custom JWT, we may not have the name in the principal
        return getCurrentUserEmail();
    }

    /**
     * Get the current user's roles from the JWT token
     */
    public List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(authority -> authority.startsWith("ROLE_"))
                    .map(authority -> authority.substring(5))
                    .filter(UserRole::isValidRole)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Get the current user's roles as UserRole enums
     */
    public List<UserRole> getCurrentUserRoleEnums() {
        return getCurrentUserRoles().stream()
                .map(UserRole::fromRoleName)
                .filter(role -> role != null)
                .collect(Collectors.toList());
    }

    /**
     * Check if the current user has a specific role
     */
    public boolean hasRole(String role) {
        return getCurrentUserRoles().contains(role);
    }

    /**
     * Check if the current user has a specific role enum
     */
    public boolean hasRole(UserRole role) {
        return hasRole(role.getRoleName());
    }

    /**
     * Check if the current user has any of the specified roles
     */
    public boolean hasAnyRole(String... roles) {
        List<String> userRoles = getCurrentUserRoles();
        for (String role : roles) {
            if (userRoles.contains(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the current user has any of the specified role enums
     */
    public boolean hasAnyRole(UserRole... roles) {
        List<String> userRoles = getCurrentUserRoles();
        for (UserRole role : roles) {
            if (userRoles.contains(role.getRoleName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the current user has the highest privilege (SuperAdmin)
     */
    public boolean isSuperAdmin() {
        return hasRole(UserRole.SUPER_ADMIN);
    }

    /**
     * Check if the current user has admin privileges (SuperAdmin or Admin)
     */
    public boolean isAdmin() {
        return hasAnyRole(UserRole.SUPER_ADMIN, UserRole.ADMIN);
    }

    /**
     * Get the current user's object ID from Azure AD
     */
    public String getCurrentUserObjectId() {
        Jwt token = getCurrentUserToken();
        if (token != null) {
            return token.getClaimAsString("oid");
        }
        return null;
    }

    /**
     * Get the tenant ID from the JWT token
     */
    public String getTenantId() {
        Jwt token = getCurrentUserToken();
        if (token != null) {
            return token.getClaimAsString("tid");
        }
        return null;
    }

    /**
     * Check if the current user is authenticated
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() &&
               !(authentication.getPrincipal() instanceof String &&
                 "anonymousUser".equals(authentication.getPrincipal()));
    }
}
