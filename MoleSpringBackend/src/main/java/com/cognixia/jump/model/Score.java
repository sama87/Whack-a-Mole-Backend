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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import io.swagger.v3.oas.annotations.media.Schema;

@Table(name = "score")
@Entity
public class Score implements Serializable, Comparable<Score> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Size(min = 1)
	public Long id;

	@Schema(description = "Relationship with the User Entity - ManyToOne")

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	public User user;
		@NotBlank
//	@Column(nullable = false)
	@Schema(description = "Difficulty of Whack-a-Mole", example = "easy, medium, hard")
	public String difficulty;


	@NotBlank
//	@Column(name = "score_value", nullable = false)
	@Schema(description = "Can be a positive or negative integer")
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
}
