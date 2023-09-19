package com.wisedevlife.whytalkauth.service;

import com.wisedevlife.whytalkauth.model.AuthInfo;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface AuthService {
    AuthInfo signIn(String email, String password);

    void logout(String accessToken);

    AuthInfo refreshAccessToken(String refreshToken);

    String removeBearerPrefix(String token);

    AuthInfo verifyOauthUser(OAuth2AuthenticationToken token);
}
