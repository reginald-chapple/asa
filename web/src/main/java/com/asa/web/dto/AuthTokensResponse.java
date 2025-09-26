package com.asa.web.dto;

public record AuthTokensResponse(String accessToken, String refreshToken, String tokenType) {
    public static AuthTokensResponse bearer(String access, String refresh) {
        return new AuthTokensResponse(access, refresh, "Bearer");
    }
}
