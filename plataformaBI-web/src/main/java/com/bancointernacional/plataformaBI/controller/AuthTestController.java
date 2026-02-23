package com.bancointernacional.plataformaBI.controller;

import com.bancointernacional.plataformaBI.auth.JwtTokenUtil;
import com.bancointernacional.plataformaBI.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthTestController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Test endpoint to verify JWT token authentication
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testAuthentication() {
        Map<String, Object> response = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.put("authenticated", auth.isAuthenticated());
        response.put("principal", auth.getName());
        response.put("authorities", auth.getAuthorities());

        // Get user information from JWT
        response.put("userEmail", jwtTokenUtil.getCurrentUserEmail());
        response.put("userName", jwtTokenUtil.getCurrentUserName());
        response.put("userRoles", jwtTokenUtil.getCurrentUserRoles());
        response.put("userObjectId", jwtTokenUtil.getCurrentUserObjectId());
        response.put("tenantId", jwtTokenUtil.getTenantId());

        return ResponseEntity.ok(response);
    }

    /**
     * Test endpoint that requires SuperAdmin role
     */
    @GetMapping("/admin-test")
    @PreAuthorize("hasRole(T(com.bancointernacional.plataformaBI.auth.UserRole).SUPER_ADMIN.getRoleName())")
    public ResponseEntity<Map<String, Object>> testAdminAccess() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Access granted - you have SuperAdmin role");
        response.put("userEmail", jwtTokenUtil.getCurrentUserEmail());
        response.put("userRoles", jwtTokenUtil.getCurrentUserRoles());
        response.put("userRoleEnums", jwtTokenUtil.getCurrentUserRoleEnums());
        response.put("isSuperAdmin", jwtTokenUtil.isSuperAdmin());

        return ResponseEntity.ok(response);
    }

    /**
     * Test endpoint that requires any admin role
     */
    @GetMapping("/manager-test")
    @PreAuthorize("hasAnyRole(T(com.bancointernacional.plataformaBI.auth.UserRole).SUPER_ADMIN.getRoleName(), T(com.bancointernacional.plataformaBI.auth.UserRole).ADMIN.getRoleName())")
    public ResponseEntity<Map<String, Object>> testManagerAccess() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Access granted - you have admin privileges");
        response.put("userEmail", jwtTokenUtil.getCurrentUserEmail());
        response.put("userRoles", jwtTokenUtil.getCurrentUserRoles());
        response.put("isAdmin", jwtTokenUtil.isAdmin());
        response.put("isSuperAdmin", jwtTokenUtil.isSuperAdmin());

        return ResponseEntity.ok(response);
    }

    /**
     * Get current user profile information from JWT token
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile() {
        Jwt token = jwtTokenUtil.getCurrentUserToken();
        Map<String, Object> profile = new HashMap<>();

        if (token != null) {
            profile.put("name", token.getClaimAsString("name"));
            profile.put("email", token.getClaimAsString("preferred_username"));
            profile.put("objectId", token.getClaimAsString("oid"));
            profile.put("tenantId", token.getClaimAsString("tid"));
            profile.put("roles", jwtTokenUtil.getCurrentUserRoles());
            profile.put("roleEnums", jwtTokenUtil.getCurrentUserRoleEnums());
            profile.put("isSuperAdmin", jwtTokenUtil.isSuperAdmin());
            profile.put("isAdmin", jwtTokenUtil.isAdmin());
            profile.put("issuer", token.getIssuer());
            profile.put("audience", token.getAudience());
            profile.put("issuedAt", token.getIssuedAt());
            profile.put("expiresAt", token.getExpiresAt());
        }

        return ResponseEntity.ok(profile);
    }

    /**
     * Test role-based access with enum usage
     */
    @GetMapping("/role-test")
    public ResponseEntity<Map<String, Object>> testRoleAccess() {
        Map<String, Object> response = new HashMap<>();

        // Test enum-based role checking
        response.put("hasSuperAdminRole", jwtTokenUtil.hasRole(UserRole.SUPER_ADMIN));
        response.put("hasAdminRole", jwtTokenUtil.hasRole(UserRole.ADMIN));
        response.put("hasUserRole", jwtTokenUtil.hasRole(UserRole.USER));

        // Test convenience methods
        response.put("isSuperAdmin", jwtTokenUtil.isSuperAdmin());
        response.put("isAdmin", jwtTokenUtil.isAdmin());

        // Show all available roles
        response.put("availableRoles", UserRole.getAllRoleNames());
        response.put("userRoles", jwtTokenUtil.getCurrentUserRoles());
        response.put("userRoleEnums", jwtTokenUtil.getCurrentUserRoleEnums());

        return ResponseEntity.ok(response);
    }
}
