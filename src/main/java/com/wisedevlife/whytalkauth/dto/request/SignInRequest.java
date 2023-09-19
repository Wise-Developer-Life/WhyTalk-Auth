package com.wisedevlife.whytalkauth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SignInRequest(@NotNull @NotEmpty String email, @NotNull @NotEmpty String password) {}
