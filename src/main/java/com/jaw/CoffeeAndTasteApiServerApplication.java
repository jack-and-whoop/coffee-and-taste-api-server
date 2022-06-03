package com.jaw;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = @Server(url = "https://coffee-and-taste.kro.kr"))
@SpringBootApplication
public class CoffeeAndTasteApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeAndTasteApiServerApplication.class, args);
	}

}
