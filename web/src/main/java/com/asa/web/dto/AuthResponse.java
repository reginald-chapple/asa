package com.asa.web.dto;

public record AuthResponse(
    String accessToken,
    String tokenType
) { public static AuthResponse bearer(String at) { return new AuthResponse(at, "Bearer"); } }
