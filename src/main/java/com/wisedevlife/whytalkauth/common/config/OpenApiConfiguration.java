package com.wisedevlife.whytalkauth.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info =
                @Info(
                        title = "Why Talk Authentication API",
                        version = "v1.0",
                        description = "Login, Logout, Token Related API",
                        contact =
                                @Contact(
                                        name = "WiseDeveloperLife Inc.",
                                        url = "https://github.com/Wise-Developer-Life"),
                        license =
                                @License(
                                        name = "Apache 2.0",
                                        url = "https://www.apache.org/licenses/LICENSE-2.0.html")))
public class OpenApiConfiguration {}
