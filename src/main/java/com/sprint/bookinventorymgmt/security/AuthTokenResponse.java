package com.sprint.bookinventorymgmt.security;

public class AuthTokenResponse {

    private final String tokenType;
    private final String accessToken;
    private final long expiresAt;
    private final AuthenticatedUserResponse user;

    public AuthTokenResponse(String tokenType, String accessToken, long expiresAt, AuthenticatedUserResponse user) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public AuthenticatedUserResponse getUser() {
        return user;
    }
}
