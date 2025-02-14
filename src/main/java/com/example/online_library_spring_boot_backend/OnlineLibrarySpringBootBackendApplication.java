package com.example.online_library_spring_boot_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Online Library", version = "v1", description = "Book Management"))
@SpringBootApplication
public class OnlineLibrarySpringBootBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineLibrarySpringBootBackendApplication.class, args);
	}

}
