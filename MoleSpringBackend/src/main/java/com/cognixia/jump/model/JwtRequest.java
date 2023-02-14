package com.cognixia.jump.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtRequest implements Serializable {
	
	private static final long serialVersionUID = 1l;
	
	@Schema(description = "Username to be authenticated")
	private String username;
	
	@Schema(description = "Password to be authenticated")
	private String password;
	
	public JwtRequest() {
		
	}
	
	public JwtRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
