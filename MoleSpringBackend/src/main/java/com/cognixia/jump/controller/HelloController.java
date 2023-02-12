package com.cognixia.jump.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping({"/hello"})
	public String test(Authentication auth) { 
		return "Hello " + auth.getName();
	}
	
}
