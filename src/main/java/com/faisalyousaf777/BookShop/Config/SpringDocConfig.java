package com.faisalyousaf777.BookShop.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition
public class SpringDocConfig {
	
	@Bean
	public OpenAPI api() {
		return new OpenAPI()
				.info(new Info()
						.title("BookShop Application")
						.description("Developed by Faisal Yousaf as an Assignment Project")
						.summary("This is a Simple Spring Boot Application for BookShop with CRUD Operations. " +
								"I have written Unit and Integration Tests for this application and implemented " +
								"Swagger OpenAPI 3")
						.contact(new Contact()
								.name("Faisal Yousaf")
								.email("faisalyousaf777@gmail.com")
								.url("https://www.baeldung.com")
						)
						.version("v1.0.0")
						.license(new License()
								.name("Apache 2.0")
								.url("http://springdoc.org")
						)
				)
				.addServersItem(new Server()
						.url("http://localhost:8080")
						.description("Localhost Server URL")
				);
	}
}
