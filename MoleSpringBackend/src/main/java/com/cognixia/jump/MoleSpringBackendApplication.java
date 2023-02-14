package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition
@SpringBootApplication
public class MoleSpringBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoleSpringBackendApplication.class, args);
	}
}
