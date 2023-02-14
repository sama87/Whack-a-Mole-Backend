package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "users")
@Entity
public class User implements Serializable {
	public User(String username, String password, List<Score> scores) {
//		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = true;
		this.scores = scores;
	}

//	private static final long serialVerisionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
//	@Column(unique = true, nullable = false)
//	@NotBlank
	public String username;
	
//	@Column(nullable = false)
//	@NotBlank
	public String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public List<Score> scores;

	@Column(columnDefinition = "boolean default true")
	public boolean enabled;

	public User() {
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.enabled = true;
	}

//	public Long getId() {
//		return id;
//	}
//
	public String getUsername() {
		return username;
	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
	public String getPassword() {
		return password;
	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
	public boolean isEnabled() {
		return enabled;
	}
//
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}
	

	
	

}
