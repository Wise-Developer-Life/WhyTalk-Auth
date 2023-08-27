package com.wisedevlife.whytalkauth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequest(@NotNull @NotEmpty String refreshToken) {}
