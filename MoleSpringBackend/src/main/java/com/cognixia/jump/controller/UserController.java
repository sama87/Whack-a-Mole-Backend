package com.cognixia.jump.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService service;
	
	//Creating a new user
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user){
		
		//Instantiate a user
		User created = service.newUser(user);
		
		//return a response in this post mapping the user that was created within the body of the request
		return ResponseEntity.status(200).body(created);
	}
	
	@GetMapping("/user/{id}")
	public User loginToUser(@Valid @PathVariable int id){
		
		return service.getUserByID(id);
	}
}
