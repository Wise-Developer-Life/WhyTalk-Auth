package com.wisedevlife.whytalkauth.controller;

import java.util.Map;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    @GetMapping("/")
    public Map<String, Object> current(OAuth2AuthenticationToken token) {
        return token.getPrincipal().getAttributes();
    }
}
