package com.cognixia.jump.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class ScoreDTO {

	@Schema(description = "Temporary placeholder for the username and score data")
	public int score;
	public String difficulty;
	public int id;
	public String username;

	public ScoreDTO(int score, String difficulty, String username) {
		this.score = score;
		this.difficulty = difficulty;
		this.username = username;
	}
}
