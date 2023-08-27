package com.wisedevlife.whytalkauth.controller;

import com.wisedevlife.whytalkauth.common.helper.ResponseHandler;
import com.wisedevlife.whytalkauth.dto.request.RefreshTokenRequest;
import com.wisedevlife.whytalkauth.dto.request.SignInRequest;
import com.wisedevlife.whytalkauth.dto.response.RefreshTokenResponse;
import com.wisedevlife.whytalkauth.dto.response.ReturnResponse;
import com.wisedevlife.whytalkauth.dto.response.SignInResponse;
import com.wisedevlife.whytalkauth.entity.AuthInfo;
import com.wisedevlife.whytalkauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in user and get JWT token")
    public ResponseEntity<ReturnResponse<SignInResponse>> signIn(
            @Valid @RequestBody SignInRequest request) {

        AuthInfo authInfo = authService.signIn(request.username(), request.password());
        SignInResponse response = SignInResponse.ofAuthInfo(authInfo);

        return ResponseHandler.success(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out user")
    public ResponseEntity<ReturnResponse<String>> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authService.removeBearerPrefix(authHeader);
        authService.logout(token);

        return ResponseHandler.success();
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token")
    public ResponseEntity<ReturnResponse<RefreshTokenResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {

        AuthInfo authInfo = authService.refreshAccessToken(request.refreshToken());
        RefreshTokenResponse response = RefreshTokenResponse.ofAuthInfo(authInfo);

        return ResponseHandler.success(response);
    }
}
