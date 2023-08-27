package com.wisedevlife.whytalkauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenInfo {
    private String refreshToken;

    public static AccessTokenInfo of(String refreshToken) {
        return AccessTokenInfo.builder().refreshToken(refreshToken).build();
    }
}
