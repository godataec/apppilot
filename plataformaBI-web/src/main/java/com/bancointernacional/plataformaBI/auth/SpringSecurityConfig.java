package com.bancointernacional.plataformaBI.auth;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final Logger springSecurityConf = LogManager.getLogger(SpringSecurityConfig.class);

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    @Order(2)
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        springSecurityConf.info("Configuring JWT Token Validation with Custom JWT!");

        http
                .requestMatchers(matchers -> matchers
                        .antMatchers("/api/**", "/**", "/actuator/**", "/swagger-ui/**", "/v3/api-docs/**")
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .antMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .antMatchers("/api/auth/**").permitAll() // Allow auth endpoints without JWT
                        .antMatchers("/auth/**").permitAll()
                        .antMatchers("/settings/color", "/api/settings/color").permitAll() // Allow color settings for logged out users
                        .antMatchers("/saml/**").hasAnyRole(UserRole.SUPER_ADMIN.getRoleName(), UserRole.ADMIN.getRoleName(), UserRole.USER.getRoleName())
                        .antMatchers("/auth-test/**").hasRole(UserRole.SUPER_ADMIN.getRoleName())
                        .antMatchers("/template/**", "/api/template/**").hasAnyRole(UserRole.SUPER_ADMIN.getRoleName(), UserRole.ADMIN.getRoleName(), UserRole.USER.getRoleName())
                        .antMatchers("/users/**", "/api/users/**").hasAnyRole(UserRole.SUPER_ADMIN.getRoleName(), UserRole.ADMIN.getRoleName())
                        .antMatchers("/settings/sync-azure-ad-users", "/api/settings/sync-azure-ad-users").hasAnyRole(UserRole.SUPER_ADMIN.getRoleName(), UserRole.ADMIN.getRoleName())
                        .antMatchers("/period/**", "/process/**", "/audit/**", "/api/period/**", "/api/process/**", "/api/audit/**").hasAnyRole(
                                UserRole.SUPER_ADMIN.getRoleName(),
                                UserRole.ADMIN.getRoleName(), UserRole.USER.getRoleName())
                        .antMatchers("/utility/**", "/api/utility/**").hasAnyRole(
                                UserRole.SUPER_ADMIN.getRoleName(),
                                UserRole.ADMIN.getRoleName(), UserRole.USER.getRoleName())
                        .antMatchers("/actions/**", "/attachments/**", "/process-policies/**",
                                   "/api/actions/**", "/api/attachments/**", "/api/process-policies/**").hasAnyRole(
                                UserRole.SUPER_ADMIN.getRoleName(),
                                UserRole.ADMIN.getRoleName(), UserRole.USER.getRoleName())
                        .antMatchers("/user-answers/**", "/user-questions/**",
                                   "/api/user-answers/**", "/api/user-questions/**").hasAnyRole(
                                UserRole.SUPER_ADMIN.getRoleName(),
                                UserRole.ADMIN.getRoleName(),
                                UserRole.USER.getRoleName())
                        .antMatchers("/policies/**", "/questionnaires/**", "/question-options/**",
                                   "/api/policies/**", "/api/questionnaires/**", "/api/question-options/**").hasAnyRole(
                                UserRole.SUPER_ADMIN.getRoleName(),
                                UserRole.ADMIN.getRoleName(), UserRole.USER.getRoleName())
                        .antMatchers("/emails/**", "/reports/**", "/api/emails/**", "/api/reports/**").hasAnyRole(
                                UserRole.SUPER_ADMIN.getRoleName(),
                                UserRole.ADMIN.getRoleName(), UserRole.USER.getRoleName())
                        .antMatchers("/policy-questionnaires/**", "/api/policy-questionnaires/**").hasAnyRole(
                                UserRole.SUPER_ADMIN.getRoleName(), UserRole.ADMIN.getRoleName(), UserRole.USER.getRoleName())
                        .antMatchers("/register-user/**", "/api/register-user/**").hasAnyRole(UserRole.USER.getRoleName())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Configure allowed origins from application properties
        if ("*".equals(allowedOrigins)) {
            config.addAllowedOriginPattern("*");
            springSecurityConf.info("Configuring CORS to allow ALL origins!");
        } else {
            String[] origins = allowedOrigins.split(",");
            config.setAllowedOrigins(Arrays.asList(origins));
            springSecurityConf.info("Configuring CORS permissions for origins: " + allowedOrigins);
        }

        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With",
                "X-Content-Type-Options",
                "X-Frame-Options",
                "X-XSS-Protection",
                "Pragma",
                "Expires",
                "X-Request-ID",
                "Cache-Control",
                "X-Graph-Token"
        ));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
