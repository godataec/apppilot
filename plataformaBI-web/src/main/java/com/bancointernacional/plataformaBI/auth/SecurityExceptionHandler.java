package com.bancointernacional.plataformaBI.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class SecurityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(SecurityExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", "Authentication failed. Please provide a valid token.");
        errorResponse.put("path", "/api");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
        logger.warn("OAuth2 authentication failed: {}", ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", "Invalid or expired token. Please re-authenticate.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<Map<String, Object>> handleJwtValidationException(JwtValidationException ex) {
        logger.warn("JWT validation failed: {}", ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", "Token validation failed. Please provide a valid token.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
        logger.warn("Access denied: {}", ex.getMessage());

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.FORBIDDEN.value());
        errorResponse.put("error", "Forbidden");
        errorResponse.put("message", "Access denied. You don't have permission to access this resource.");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
