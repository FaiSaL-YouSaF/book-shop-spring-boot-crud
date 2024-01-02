package com.faisalyousaf777.BookShop.Controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	@Operation(summary = "Display Index Page: Renders and shows the main index page of the application")
	@GetMapping("/")
	public String homePage() {
		return "Welcome to the Home Page.";
	}
}
