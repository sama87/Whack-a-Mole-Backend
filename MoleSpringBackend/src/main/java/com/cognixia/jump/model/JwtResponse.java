package com.cognixia.jump.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtResponse implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "JWT Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMyIsImV4cCI6MTY3NjM1MTE1OCwiaWF0IjoxNjc2MzE1MTU4fQ.4dLD1NyxWTNDv4L54OpySPOt-ZpcOZ-ZjZOullAOrF4")
	private String jwt;
	
	public JwtResponse(String jwt) {
		this.jwt = jwt;
	}
	
	public String getJwt() {
		return jwt;
	}
	
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
