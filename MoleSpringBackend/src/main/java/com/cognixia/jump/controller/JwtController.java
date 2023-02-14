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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class JwtController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Operation(summary = "Authenticates login information",
			description = "Gets username and password credentials from the login endpoint. The username and password is then passed into the JwtRequest which is one of the classes that are in the model package. It then is passed to the Authentication Manager to process the Authentication Request to determine the validity of the username and password. If the username and password is invalid it throws a BadCredentialsException or if a random error occurs, it will throw an Exception('OtherProblem'). if it passes, the user credentials is passed to the UserDetails class which is then used to generate the JWT Token")
	@ApiResponse(responseCode = "200", description = "Returns a JWT Token")
	@PostMapping("/login")
	public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest request) throws Exception {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword() )
					);
		}
		catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Other problem");
		}
		
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
	
		final String jwt = jwtUtil.generateTokens(userDetails);
		
		return ResponseEntity.status(200).body( new JwtResponse(jwt));
	}
}















