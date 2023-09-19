package com.wisedevlife.whytalkauth.dto.response;

import com.wisedevlife.whytalkauth.model.AuthInfo;

public record OauthSignInResponse(String accessToken, String refreshToken, long expiredIn) {
    public static OauthSignInResponse ofAuthInfo(AuthInfo authInfo) {
        return new OauthSignInResponse(
                authInfo.getAccessToken(),
                authInfo.getRefreshToken(),
                authInfo.getExpiredIn().getSeconds());
    }
}
