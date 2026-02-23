package com.bancointernacional.plataformaBI.auth;

import com.bancointernacional.plataformaBI.service.JwtGeneratorService;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT Authentication Filter
 * Intercepts requests and validates JWT tokens
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtGeneratorService jwtGeneratorService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = extractJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtGeneratorService.validateToken(jwt)) {
                Claims claims = jwtGeneratorService.extractClaims(jwt);

                String email = claims.getSubject();

                // Extract roles from claims
                List<String> roles = extractRoles(claims);

                // Create granted authorities
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                // Create authentication token
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.debug("JWT authentication successful for user: {}", email);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication from JWT token", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract JWT token from Authorization header
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * Extract roles from JWT claims
     */
    @SuppressWarnings("unchecked")
    private List<String> extractRoles(Claims claims) {
        try {
            Object rolesObj = claims.get("roles");

            if (rolesObj instanceof List) {
                return (List<String>) rolesObj;
            }
        } catch (Exception e) {
            logger.warn("Error extracting roles from JWT claims", e);
        }

        return new ArrayList<>();
    }
}

