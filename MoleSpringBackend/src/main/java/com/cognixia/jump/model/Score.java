package com.cognixia.jump.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "score")
@Entity
public class Score implements Serializable, Comparable<Score> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	public User user;
	//	@NotBlank
//	@Column(nullable = false)
	public String difficulty;
	@JsonProperty("score")
	public Integer scoreValue;
	public String username;
	public long created;

	public Score() {
	}

	@Override
	public int compareTo(Score other) {
		return other.scoreValue.compareTo(scoreValue);
	}


//	public User getUserID() {
//		return user;
//	}
//
//	public void setUserID(User userID) {
//		this.user = userID;
//	}
//
//	public String getDifficulty() {
//		return difficulty;
//	}
//
//	public void setDifficulty(String difficulty) {
//		this.difficulty = difficulty;
//	}
//
//	public Integer getScoreValue() {
//		return scoreValue;
//	}
//
//	public void setScoreValue(Integer scoreValue) {
//		this.scoreValue = scoreValue;
//	}
//
//	public Long getScoreID() {
//		return scoreID;
//	}


}
