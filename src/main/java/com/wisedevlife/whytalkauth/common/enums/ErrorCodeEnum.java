package com.wisedevlife.whytalkauth.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    SYSTEM_ERROR(-1, "system error"),
    AUTH_JWT_FAILED(1001, "auth jwt token failed"),
    AUTH_JWT_INVALID(1002, "jwt token invalid"),
    AUTH_JWT_EXPIRED(1003, "jwt token expired"),
    AUTH_JWT_UNSUPPORTED(1004, "jwt token unsupported"),
    AUTH_JWT_EMPTY_CLAIMS(1004, "jwt token with no claims"),
    OAUTH_AUTHENTICATION_FAILED(1005, "OAuth 2.0 authentication failed");

    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
