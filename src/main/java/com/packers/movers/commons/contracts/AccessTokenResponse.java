package com.packers.movers.commons.contracts;


import com.packers.movers.commons.utils.JsonUtils;

public class AccessTokenResponse extends ContractBase {
    private final String access_token;
    private final int expires_in;
    private final String token_type;
    private final String scope;
    private final String refresh_token;

    public AccessTokenResponse(String accessToken, int expiresIn, String tokenType, String scope, String refreshToken) {
        this.access_token = accessToken;
        this.expires_in = expiresIn;
        this.token_type = tokenType;
        this.scope = scope;
        this.refresh_token = refreshToken;
    }

    public AccessTokenResponse(String accessToken, int expiresIn) {
        this(accessToken, expiresIn, null, null, null);
    }

    public static AccessTokenResponse fromJson(String json) {
        return JsonUtils.deserialize(json, AccessTokenResponse.class);
    }

    public String getAccessToken() {
        return access_token;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public String getTokenType() {
        return token_type;
    }

    public String getScope() {
        return scope;
    }

    public String getRefreshToken() {
        return refresh_token;
    }
}
