package com.wisedevlife.whytalkauth.model;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthInfo {
    private String accessToken;
    private String refreshToken;
    private Duration expiredIn;

    public static AuthInfo of(String accessToken, String refreshToken, Duration expiredIn) {
        return AuthInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredIn(expiredIn)
                .build();
    }
}
