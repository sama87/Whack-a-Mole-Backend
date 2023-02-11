package com.cognixia.jump.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	
	@Autowired
	UserRepository repo;
	
	public ResponseEntity<?> newUser (String username, String password) {
		
		User newUser = new User(username, password);
		return ResponseEntity.status(200).body(repo.save(newUser));
	}
	
	//for admin?
	public List<User> getUsers (){
		
		List<User> listAll = new ArrayList<User>();
		listAll = repo.findAll();
		
		return listAll;
	}
	
	public User getUserByID (int id) {
		
		User found = repo.getReferenceById(id);
		
		if (found != null) {
			
			return found;
		} else {
			
			return new User();
		}
	}
}
