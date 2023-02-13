package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Score;
import com.cognixia.jump.repository.ScoreRepository;

@RestController
@RequestMapping("/api")
public class ScoreController {
	
	@Autowired
	ScoreRepository repo;
	
	@GetMapping("/scores")
	public void getScores() {
		List<Score> all = repo.findAll();
		all.forEach(score -> {
			System.out.println("score.user = " + score.user.userName);
		});
	}
	
	@PostMapping("/submit") 
		public ResponseEntity<?> submitScore(@RequestBody Score score ){
			
			Score created = repo.save(score);
			
			return ResponseEntity.status(201).body(created);
		
	}
}
