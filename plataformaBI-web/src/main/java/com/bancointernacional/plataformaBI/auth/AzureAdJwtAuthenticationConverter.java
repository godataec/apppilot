package com.bancointernacional.plataformaBI.auth;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AzureAdJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                defaultGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractRolesFromToken(jwt).stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Collection<GrantedAuthority> extractRolesFromToken(Jwt jwt) {
        Object rolesClaim = jwt.getClaim("roles");
        if (rolesClaim instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) rolesClaim;
            return roles.stream()
                    .filter(UserRole::isValidRole)
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }
        Object groupsClaim = jwt.getClaim("groups");
        if (groupsClaim instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> groups = (List<String>) groupsClaim;
            return groups.stream()
                    .filter(UserRole::isValidRole)
                    .map(group -> new SimpleGrantedAuthority("ROLE_" + group))
                    .collect(Collectors.toList());

        }
        return Collections.emptyList();
    }
}
