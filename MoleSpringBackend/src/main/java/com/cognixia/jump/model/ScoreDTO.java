package com.cognixia.jump.model;

public class ScoreDTO {

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
