package com.wisedevlife.whytalkauth.common.constants;

import java.time.Duration;

public class AuthConstant {

    private AuthConstant() {}

    public static final Duration ACCESS_EXPIRATION_DURATION = Duration.ofMinutes(5);
    public static final Duration REFRESH_EXPIRATION_DURATION = Duration.ofDays(7);
    public static final String JWT_HEADER_PREFIX = "Bearer ";
}
