package com.bancointernacional.plataformaBI.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/saml")
public class SamlTestController {

    private static final Logger logger = LogManager.getLogger(SamlTestController.class);

    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> userInfo = new HashMap<>();

        if (authentication != null && authentication.isAuthenticated()) {
            userInfo.put("authenticated", true);
            userInfo.put("name", authentication.getName());
            userInfo.put("authorities", authentication.getAuthorities());
            
            if (authentication.getPrincipal() instanceof Saml2AuthenticatedPrincipal) {
                Saml2AuthenticatedPrincipal principal = (Saml2AuthenticatedPrincipal) authentication.getPrincipal();
                userInfo.put("attributes", principal.getAttributes());
                userInfo.put("sessionIndexes", principal.getSessionIndexes());
                userInfo.put("registrationId", principal.getRelyingPartyRegistrationId());
                
                logger.info("SAML User Info requested: {}", principal.getName());
            }
        } else {
            userInfo.put("authenticated", false);
        }

        return userInfo;
    }

    @GetMapping("/metadata")
    public Map<String, String> getMetadataInfo() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("message", "Para obtener los metadatos del Service Provider, visita:");
        metadata.put("url", "/saml2/service-provider-metadata/azure");
        return metadata;
    }
}

