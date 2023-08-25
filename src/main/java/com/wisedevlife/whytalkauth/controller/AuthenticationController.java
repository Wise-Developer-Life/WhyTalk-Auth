package com.wisedevlife.whytalkauth.controller;

import com.wisedevlife.whytalkauth.dto.request.SignInRequest;
import com.wisedevlife.whytalkauth.dto.response.RefreshTokenResponse;
import com.wisedevlife.whytalkauth.dto.response.ReturnResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @PostMapping("/sign-in")
    @Operation(summary = "Sign in user and get JWT token")
    public ResponseEntity<ReturnResponse<String>> signIn(@RequestBody SignInRequest signInRequest) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out user")
    public ResponseEntity<ReturnResponse<String>> logout() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token")
    public ResponseEntity<ReturnResponse<RefreshTokenResponse>> refresh() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
