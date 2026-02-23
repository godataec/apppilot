package com.bancointernacional.plataformaBI.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
    // This configuration enables AspectJ auto-proxying for the CRUD logging aspect
}
