package com.n11.restaurantservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                description = "Search and manage restaurants",
                title = "Restaurant API",
                version = "1.0.0",
                license = @License(
                        name = "The UNLICENSE",
                        url = "https://unlicense.org"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
        }
)
public class OpenAPIConfig {
}
