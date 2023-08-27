package com.wisedevlife.whytalkauth.service;

import java.time.Duration;
import java.util.Map;

public interface JwtService {
    void validateToken(String token);

    <T> T getClaimBody(String token, Class<T> t);

    String getClaimSubject(String token);

    String generateToken(Map<String, Object> claims, String username, Duration duration);

    String generateToken(String username, Duration duration);
}
