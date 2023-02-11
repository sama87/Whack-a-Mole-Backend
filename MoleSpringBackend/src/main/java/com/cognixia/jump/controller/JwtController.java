package com.cognixia.jump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.JwtRequest;
import com.cognixia.jump.model.JwtResponse;
import com.cognixia.jump.util.JwtUtil;

@RestController
public class JwtController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest request) throws Exception {
		
		try {
			System.out.println("******************");
			System.out.println(request.getUsername());
			System.out.println(request.getPassword());
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword() )
					);
		}
		
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Other problem");
		}
		
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
	
		final String jwt = jwtUtil.generateTokens(userDetails);
		
		return ResponseEntity.status(201).body( new JwtResponse(jwt));
	}
}















