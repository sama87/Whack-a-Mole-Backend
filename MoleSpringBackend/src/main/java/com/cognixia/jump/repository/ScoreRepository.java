package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
	
//	@Query("select s.user, s.scoreValue from Score s")
//	public List<Score> getAllScores();
	
	@Query("select s from Score s where difficulty = ?1")
	public List<Score> getScoresByDifficulty(String difficulty);
	
	@Query("select s.user, s.difficulty, s.scoreValue from Score s, User u where u.id = s.user and u.userName = ?1 and s.difficulty = ?2")
	public List<Score> getScoresByUsernameAndDifficulty(String username, String difficulty);	
}
