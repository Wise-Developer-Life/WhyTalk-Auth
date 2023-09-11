package com.wisedevlife.whytalkauth.service;

import com.wisedevlife.whytalkauth.model.AuthInfo;

public interface AuthService {
    AuthInfo signIn(String username, String password);

    void logout(String accessToken);

    AuthInfo refreshAccessToken(String refreshToken);

    String removeBearerPrefix(String token);
}
