package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Score implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer scoreID;
	
	@NotBlank
	@Column(name = "difficulty", columnDefinition = "varchar(10) DEFAULT 'Difficulty'", nullable = false)
	private String difficulty;
	
	@NotBlank
	@Column(name = "scoreValue")
	private int scoreValue;	
	
	@ManyToOne
	@JoinColumn(name = "userID", nullable = false, referencedColumnName = "ID")
	private User user;
	
	public Score () {
		
	}

	public Score(int scoreID, @NotBlank String difficulty, @NotBlank int scoreValue, User user) {
		super();
		this.scoreID = scoreID;
		this.difficulty = difficulty;
		this.scoreValue = scoreValue;
		this.user = user;
	}

	/**
	 * @return the scoreID
	 */
	public Integer getScoreID() {
		return scoreID;
	}

	/**
	 * @return the difficulty
	 */
	public String getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the scoreValue
	 */
	public int getScoreValue() {
		return scoreValue;
	}

	/**
	 * @param scoreValue the scoreValue to set
	 */
	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User userID) {
		this.user = userID;
	}

	
}
