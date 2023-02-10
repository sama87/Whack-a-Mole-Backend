package com.cognixia.jump.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	
	@Autowired
	UserRepository repo;
	
	public User createNewUser (User user) {
		
		User newUser = new User(user.getUserName(), user.getPassword());
		repo.save(newUser);
		return newUser;
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
