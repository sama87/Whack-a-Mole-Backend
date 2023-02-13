package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.model.Score;
import com.cognixia.jump.repository.ScoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository repo;

	@Autowired
	ScoreRepository scorerepo;
	
	@GetMapping("/user")
	public void getUsers() {
		List<User> all = repo.findAll();
		all.forEach(user -> {
			System.out.println("user.username = " + user.userName);
		});
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return repo.findAll();
	}

//	@GetMapping("/scores")
//	public List<Score> getScores() {
//		return scorerepo.findAll();
//	}

	@GetMapping("/populate")
	public String populateUser() {
		ArrayList<Score> scores = new ArrayList<>();


		User newUser = new User("miguel", "this", scores);

		Score newScore = new Score();
		newScore.user = newUser;
		newScore.scoreValue = 0;
		newScore.difficulty = "normal";

		repo.save(newUser);
		scorerepo.save(newScore);
		return "Created";

	}
	
	
}
