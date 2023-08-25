package com.wisedevlife.whytalkauth.dto.request;

public record SignInRequest(
        String username,
        String password
) {
}
