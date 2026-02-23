package com.bancointernacional.plataformaBI.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token Response DTO
 * Response structure for JWT token generation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {

    private boolean success;
    private String token;
    private UserInfoDTO userInfo;
    private String message;
    private String error;

    /**
     * Create a successful token response
     */
    public static TokenResponse success(String token, UserInfoDTO userInfo) {
        return TokenResponse.builder()
                .success(true)
                .token(token)
                .userInfo(userInfo)
                .build();
    }

    /**
     * Create an error token response
     */
    public static TokenResponse error(String errorMessage) {
        return TokenResponse.builder()
                .success(false)
                .error(errorMessage)
                .build();
    }
}

