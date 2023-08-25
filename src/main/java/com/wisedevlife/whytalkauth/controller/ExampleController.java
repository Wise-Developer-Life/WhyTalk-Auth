package com.wisedevlife.whytalkauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@Tag(name = "Example API", description = "Example API")
public class ExampleController {
    @GetMapping
    @Operation(summary = "Example API")
    public String example() {
        return "Hello World!";
    }
}
