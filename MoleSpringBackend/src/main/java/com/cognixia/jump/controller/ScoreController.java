package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Score;
import com.cognixia.jump.service.ScoreService;

@RestController
@RequestMapping("/api")
public class ScoreController {

	@Autowired
	ScoreService service;
	
	@GetMapping("/allscores")
	public List<Score> showAllScores(){
		
		return service.getAllScores();
	}
	
	@GetMapping("/allscores/{difficulty}")
	public List<Score> getScoresByDifficulty(@Valid @PathVariable String difficulty){
		
		return service.getScoresByDifficulty(difficulty);
	}
	
	//needs to be validate first
	@GetMapping("/allscores/{username}/{difficulty}")
	public ResponseEntity<?> getScoresByIdAndDifficulty(@Valid @PathVariable("username") String username, @PathVariable("difficulty") String difficulty){
		
		return ResponseEntity.status(200).body(service.getScoresByUsernameAndDifficulty(username, difficulty));
	}
	
//	@PutMapping("/allscores/{id}/{score}")
//	public ResponseEntity<?> getScoreForUser(@PathVariable("id") int id, @PathVariable("score") int scoreValue) {
//		
//		Score setScore = new Score();
//		
//		setScore = service.getScoreForUser(id, scoreValue);
//		
//		return ResponseEntity.status(200).body(setScore);
//	}
}
