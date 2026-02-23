package com.bancointernacional.plataformaBI.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login Response DTO
 * Response structure for login operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private boolean success;
    private String message;
    private String redirectUrl;
    private UserInfoDTO userInfo;
}

