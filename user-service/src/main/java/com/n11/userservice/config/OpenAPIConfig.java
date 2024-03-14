package com.n11.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                description = "User and Review API",
                title = "User/Review API",
                version = "1.0.0",
                license = @License(
                        name = "The UNLICENSE",
                        url = "https://unlicense.org"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8081"
                ),
        }
)
public class OpenAPIConfig {
}
