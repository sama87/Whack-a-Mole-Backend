package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
	
	@Query("select u.userName, s.scoreValue \r\n"
			+ "from user u, score s\r\n"
			+ "where u.userID = s.userID;")
	public List<Score> getAllScores();
	
	@Query("select u.userName, s.scoreValue \r\n"
			+ "from user u, score s \r\n"
			+ "where u.userID = s.userID \r\n"
			+ "and s.difficulty = ?1;")
	public List<Score> getScoresByDifficulty(String dfficulty);
	
	@Query("select u.userName, s.difficulty, s.scoreValue \r\n"
			+ "from score s, user u \r\n"
			+ "where s.userID = ?1 \r\n"
			+ "and difficulty = ?2;")
	public List<Score> getScoresByIdAndDifficulty(int id, String difficulty);	
}
