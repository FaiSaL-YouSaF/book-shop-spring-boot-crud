package com.faisalyousaf777.BookStore.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class IndexController {
	
	@Operation(summary = "Display Index Page: Renders and shows the main index page of the application")
	@GetMapping("/")
	public String homePage() {
		return "Welcome to the Home Page.";
	}
}
