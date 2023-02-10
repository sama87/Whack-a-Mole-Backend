package com.cognixia.jump.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Score;
import com.cognixia.jump.repository.ScoreRepository;

@Service
public class ScoreService {
	
	@Autowired
	ScoreRepository repo;
	
	//get all scores
	public List<Score> getAllScores(){
		
		return repo.findAll();
	}
	
	public List<Score> getScoresByDifficulty(String difficulty){
		
		return repo.getScoresByDifficulty(difficulty);
	}
	
//	public List<Score> getScoresByIdAndDifficulty(int id, String difficulty){
//		
//		return repo.getScoresByIdAndDifficulty(id, difficulty);
//	}
	
//	public Score getScoreForUser(int id, int value) {
//		
//		Score userScore = repo.getReferenceById(id);
//		
//		userScore.setScoreValue(value);
//		
//		return repo.save(userScore);
//	}
}
