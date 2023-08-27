package com.wisedevlife.whytalkauth.dto.response;

import com.wisedevlife.whytalkauth.model.AuthInfo;

public record SignInResponse(String accessToken, String refreshToken, long expiredIn) {
    public static SignInResponse ofAuthInfo(AuthInfo authInfo) {
        return new SignInResponse(
                authInfo.getAccessToken(),
                authInfo.getRefreshToken(),
                authInfo.getExpiredIn().getSeconds());
    }
}
