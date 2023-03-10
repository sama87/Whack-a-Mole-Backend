package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
	
	@Query("select s.user, s.scoreValue from Score s")
	List<Score> getAllScores();
	
	@Query("select u.username, s.difficulty, s.scoreValue from Score s, User u where u.id = ?1 and s.difficulty = ?2")
	List<Score> getScoresByIdAndDifficulty(int id, String difficulty);


	List<Score> findByUsername(String username);

	List<Score> getScoresByDifficulty(String difficulty);
}
