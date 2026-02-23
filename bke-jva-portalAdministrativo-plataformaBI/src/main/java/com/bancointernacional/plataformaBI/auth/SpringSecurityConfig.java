package com.bancointernacional.plataformaBI.auth;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;




@Configuration
@EnableWebSecurity
public class SpringSecurityConfig{

    private static final Logger springSecurityConf = LogManager.getLogger(SpringSecurityConfig.class);
    
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        springSecurityConf.info("Permisos de Consulta!");
        http    
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll()) // Permite acceso sin autenticación a todas las rutas
                .httpBasic(Customizer.withDefaults()); // Configura autenticación básica HTTP (opcional, puedes eliminarlo si no lo necesitas)

        return http.build();
    }
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        springSecurityConf.info("Permisos de Cors!");
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT","OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        
    }

     @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
            new CorsFilter(corsConfigurationSource()));
            bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return bean;
    }

    
}
