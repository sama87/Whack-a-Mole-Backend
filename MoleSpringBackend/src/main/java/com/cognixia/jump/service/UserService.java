package com.cognixia.jump.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.UsernameTakenException;
import com.cognixia.jump.model.JwtRequest;
import com.cognixia.jump.model.JwtResponse;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;

@Service
public class UserService {

	
	@Autowired
	UserRepository repo;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	public ResponseEntity<?> newUser (User user) throws UsernameTakenException {
		
		if(checkUserExists(user.getUserName() ) == true ) throw new  UsernameTakenException("Username isn't available");
		
		repo.save(user);
//		
		JwtRequest forNewUser = new JwtRequest(user.getUserName(), user.getPassword());
		
		final UserDetails newUser = userDetailsService.loadUserByUsername(forNewUser.getUsername());
		
		final String jwt = jwtUtil.generateTokens(newUser);
		
		return ResponseEntity.status(201).body(new JwtResponse(jwt));
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
	
	public Boolean checkUserExists(String name) {
		User found = repo.getByUserName(name);
		
		if (found != null) {
			return true;
		}
		else return false;
	}
}
