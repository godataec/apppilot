package com.bancointernacional.plataformaBI.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PlataformaBI API")
                        .description("API documentation for PlataformaBI application including Azure AD user synchronization")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Banco Internacional")
                                .email("support@bancointernacional.com"))
                        .license(new License()
                                .name("Proprietary License")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")
                                .description("JWT token for authentication. Format: Bearer {token}")));
    }
}
