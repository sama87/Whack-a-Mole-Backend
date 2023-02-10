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
		
		return service.getAllSCores();
	}
	
	@GetMapping("/allscores/{difficulty}")
	public List<Score> getScoresByDifficulty(@Valid @PathVariable String difficulty){
		
		return service.getScoresByDifficulty(difficulty);
	}
	
	//needs to be validate first
	@GetMapping("/allscores/{id}/{difficulty}")
	public ResponseEntity<?> getScoresByIdAndDifficulty(@PathVariable("id") int id, @PathVariable("difficulty") String difficulty){
		
		return ResponseEntity.status(200).body(service.getScoresByIdAndDifficulty(id, difficulty));
	}
}
