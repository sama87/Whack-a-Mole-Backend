package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	//creates a user in the database
	//public User newUser (User user);
	public Optional<User> findByUserName(String username);
	public User getByUserName(String username);
}
