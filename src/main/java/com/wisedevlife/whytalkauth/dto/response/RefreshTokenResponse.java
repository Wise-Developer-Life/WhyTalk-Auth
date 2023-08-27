package com.wisedevlife.whytalkauth.dto.response;

import com.wisedevlife.whytalkauth.model.AuthInfo;

public record RefreshTokenResponse(String accessToken, String refreshToken, long expiredIn) {
    public static RefreshTokenResponse ofAuthInfo(AuthInfo authInfo) {
        return new RefreshTokenResponse(
                authInfo.getAccessToken(),
                authInfo.getRefreshToken(),
                authInfo.getExpiredIn().getSeconds());
    }
}
