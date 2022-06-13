package com.abhishek.springsecurityjune9th.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/welcome")
	public String welcome() {
		
		return  "Welcome to Spring Security session";
	}
	
	@GetMapping("/welcome/{name}")
	public String welcomeByName(@PathVariable String name) {
		return "Welcome " + name + " to Spring Security session";
	}

	@GetMapping("/welcome/{name}/dept/{dept}")
	public String welcomeByName(@PathVariable String name, @PathVariable String dept) {
		return "Welcome " + name + " from department "+ dept + " to Spring Security session";
	}
}
