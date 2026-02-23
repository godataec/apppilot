package com.bancointernacional.plataformaBI.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to prevent SpringDoc auto-configuration
 */
@Configuration
@ConditionalOnMissingBean(name = "springdoc")
public class NoSwaggerConfig {
    // This class exists solely to prevent SpringDoc auto-configuration
    // by ensuring no SpringDoc beans are created
}
