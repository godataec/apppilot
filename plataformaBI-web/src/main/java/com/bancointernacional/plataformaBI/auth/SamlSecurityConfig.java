package com.bancointernacional.plataformaBI.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SamlSecurityConfig {

    private static final Logger samlSecurityLog = LogManager.getLogger(SamlSecurityConfig.class);

    @Value("${saml.success.redirect.url:http://localhost:4200/auth/callback}")
    private String samlSuccessRedirectUrl;

    @Bean
    @Order(1)
    SecurityFilterChain samlFilterChain(HttpSecurity http) throws Exception {
        samlSecurityLog.info("Configuring SAML2 Authentication with Azure AD!");

        http
                .requestMatchers(matchers -> matchers
                        .antMatchers("/saml2/**", "/login/saml2/**", "/saml/logout", "/saml/slo", "/", "/api/auth/token")
                )
                .cors(cors -> cors.configurationSource(request -> {
                    org.springframework.web.cors.CorsConfiguration corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOriginPatterns(java.util.Arrays.asList("*"));
                    corsConfig.setAllowedMethods(java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(java.util.Arrays.asList("*"));
                    corsConfig.setExposedHeaders(java.util.Arrays.asList("Authorization", "X-Request-ID"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .csrf(csrf -> csrf.disable()) // Disable CSRF for SAML endpoints
                .authorizeHttpRequests(authz -> authz
                        .antMatchers("/api/auth/token").authenticated()
                        .antMatchers("/saml/logout", "/saml/slo").permitAll() // Allow logout endpoints
                        .anyRequest().authenticated()
                )
                .saml2Login(saml2 -> saml2
                        .successHandler(samlAuthenticationSuccessHandler())
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/saml/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    private AuthenticationSuccessHandler samlAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            samlSecurityLog.info("SAML Authentication successful!");

            if (authentication instanceof Saml2Authentication) {
                Saml2Authentication saml2Auth = (Saml2Authentication) authentication;
                Object principalObj = saml2Auth.getPrincipal();

                if (principalObj instanceof Saml2AuthenticatedPrincipal) {
                    Saml2AuthenticatedPrincipal principal = (Saml2AuthenticatedPrincipal) principalObj;

                    samlSecurityLog.info("Authenticated user: {}", principal.getName());
                    samlSecurityLog.info("SAML Attributes: {}", principal.getAttributes());

                    // Extract roles from SAML
                    Collection<GrantedAuthority> extractedRoles = extractRolesFromSaml(principal);
                    samlSecurityLog.info("Extracted roles from SAML: {}", extractedRoles);

                    // Create new authentication with extracted roles
                    Saml2Authentication newAuth = new Saml2Authentication(
                            principal,
                            saml2Auth.getSaml2Response(),
                            extractedRoles  // Use extracted roles instead of original authorities
                    );

                    // Update SecurityContext with new authentication
                    SecurityContextHolder.getContext().setAuthentication(newAuth);

                    samlSecurityLog.info("Updated authentication with roles: {}", extractedRoles);
                }
            }

            // Redirect to configured frontend URL
            samlSecurityLog.info("Redirecting to frontend application: {}", samlSuccessRedirectUrl);
            response.sendRedirect(samlSuccessRedirectUrl);
        };
    }

    private Collection<GrantedAuthority> extractRolesFromSaml(Saml2AuthenticatedPrincipal principal) {
        // Try to extract roles from SAML attributes
        List<Object> rolesAttribute = principal.getAttribute("roles");
        if (rolesAttribute != null && !rolesAttribute.isEmpty()) {
            return rolesAttribute.stream()
                    .filter(role -> role instanceof String)
                    .map(role -> (String) role)
                    .filter(UserRole::isValidRole)
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }

        // Try to extract groups from SAML attributes
        List<Object> groupsAttribute = principal.getAttribute("groups");
        if (groupsAttribute != null && !groupsAttribute.isEmpty()) {
            return groupsAttribute.stream()
                    .filter(group -> group instanceof String)
                    .map(group -> (String) group)
                    .filter(UserRole::isValidRole)
                    .map(group -> new SimpleGrantedAuthority("ROLE_" + group))
                    .collect(Collectors.toList());
        }

        // Try Azure AD specific role claim
        List<Object> azureRoles = principal.getAttribute("http://schemas.microsoft.com/ws/2008/06/identity/claims/role");
        if (azureRoles != null && !azureRoles.isEmpty()) {
            return azureRoles.stream()
                    .filter(role -> role instanceof String)
                    .map(role -> (String) role)
                    .filter(UserRole::isValidRole)
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }

        samlSecurityLog.warn("No roles found in SAML attributes for user: {}", principal.getName());
        return Collections.emptyList();
    }
}

