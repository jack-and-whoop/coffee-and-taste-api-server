package com.jaw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Coffee and Taste",
        description = "This is a RESTful API server for Coffee and Taste",
        version = "1.0"
    )
)
public class OpenAPIConfiguration {

    @Value("${open-api.url}")
    private String url;

    @Value("${open-api.description}")
    private String description;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .addServersItem(createServer());
    }

    public Server createServer() {
        return new Server()
            .url(url)
            .description(description);
    }
}
