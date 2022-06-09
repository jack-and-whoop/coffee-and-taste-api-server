package com.jaw.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    private static final String URL_PROD = "https://coffee-and-taste.kro.kr";
    private static final String URL_DEV = "http://localhost:8080";

    @Bean
    public OpenAPI openAPI() {
        Server prod = createServer(URL_PROD, "Production");
        Server dev = createServer(URL_DEV, "Development");
        return new OpenAPI().servers(List.of(prod, dev));
    }

    public Server createServer(String url, String description) {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription(description);
        return server;
    }
}
