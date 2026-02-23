package com.bancointernacional.plataformaBI.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User Information DTO
 * Contains user details extracted from authentication
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDTO {

    private String email;
    private String name;
    private List<String> roles;
    private String objectId;
    private String tenantId;

    /**
     * Check if user has a specific role
     */
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    /**
     * Check if user has any of the specified roles
     */
    public boolean hasAnyRole(String... rolesToCheck) {
        if (roles == null) {
            return false;
        }
        for (String role : rolesToCheck) {
            if (roles.contains(role)) {
                return true;
            }
        }
        return false;
    }
}

