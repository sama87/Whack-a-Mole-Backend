package com.cognixia.jump.controller;

import com.cognixia.jump.model.*;
import com.cognixia.jump.repository.ScoreRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
public class MainController {
	@Autowired
	UserRepository userRepo;
	//	Jwt jwt = new Jwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" + ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
//	String username = "miguel";
	//	String password = "hashed";
//	String password = "U2FsdGVkX18HYydxWuCFBLslkZn2a8R51JgWX4TN2cXahyugFL74IUz5" +
//			"+0a9O8neHKxaWz7Vj9mWrSqJFxX1uLrEcI4FlTbcCfEdq9GdBNL5sXxpq" +
//			"+1BzLMwkrR+u3RktcKTLZJUaos9f3j2xVq8oA==";
//	String password = "b390264a923529cdd2012d033c790ea522effb9b15d7800d3db41effe58fd346";
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	ScoreRepository scoreRepo;
	ObjectMapper mapper = new ObjectMapper();

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("*");
//			}
//		};
//	}

//	@CrossOrigin("*")
//	@PostMapping("/login")
//	ResponseEntity<?> login(@RequestBody User user) {
////		System.out.println("user.username = " + user.username);
////		System.out.println("user.password = " + user.password);
//		User byUsername = userRepo.findByUsername(user.username);
////		System.out.println("byUsername.username = " + byUsername.username);
//		if ((null != byUsername) && (user.username.equals(byUsername.username) && (user.password.equals(byUsername.password)))) {
//			return ResponseEntity.status(HttpStatus.OK).body(jwt);
//		} else {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username or password");
//		}
//		// receive the username and password
//		// { username: "foo", password: "barencrypted"}
//		// responds with an jwt
//		// 200OK OR 401 Unauthorized
//		// {jwt: ezlkqwsdfjbnuilwerbnspiofg792364ol;kjhberfasdfc}
//	}

	@CrossOrigin("*")
	@PostMapping("/register")
	ResponseEntity<?> register(@RequestBody User user) {
		// receive the username and password
		// { username: "foo", password: "barencrypted"}
		// responds with an jwt
		// 201 or 401
		// {jwt: ezlkqwsdfjbnuilwerbnspiofg792364ol;kjhberfasdfc}

		ArrayList<Score> scores = new ArrayList<>();
		if ((null != user.username) && (null != user.password)) {
			if (userRepo.findByUsername(user.username).isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username " + user.username + " already exists");
			}
			userRepo.save(new User(user.username, user.password, scores));
			JwtRequest forNewUser = new JwtRequest(user.username, user.password);
			final UserDetails newUser = userDetailsService.loadUserByUsername(forNewUser.getUsername());
			final String jwt = jwtUtil.generateTokens(newUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(jwt));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
		}
	}


	@CrossOrigin("*")
	@GetMapping("/scores/{username}/{level}/")
	ResponseEntity<?> score(@PathVariable String username, @PathVariable String level, @RequestHeader(HttpHeaders.AUTHORIZATION) String headers) throws JsonProcessingException {
		// request
		// Authorization header token
		// response 200
		// {[{id: 1, score: 100, dificulty: "easy"},{}]}
		String jwtHeader = headers.split(" ")[1];
		System.out.println("jwtHeader = " + jwtHeader);
//		if (jwtHeader.equals(this.jwt.jwt)) {
		List<Score> byUsername = scoreRepo.findByUsername(username).stream()
		                                  .filter(score -> score.difficulty.equals(level))
		                                  .sorted(Score::compareTo)
		                                  .limit(10)
		                                  .collect(Collectors.toList());
//			byUsername.forEach(score -> {
//				System.out.println("score.scoreValue = " + score.scoreValue);
//
//			});
		return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(byUsername));
	}
//		else {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
//		}


	@CrossOrigin("*")
	@GetMapping("/highscores/{level}")
	ResponseEntity<?> highscore(@PathVariable String level) throws JsonProcessingException {
		// nothing needed
		List<Score> scores = scoreRepo.findAll().stream()
		                              .filter(score -> score.difficulty.equals(level))
                                  .sorted(Score::compareTo)
                                  .limit(10)
                                  .collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(scores));
	}

	@CrossOrigin("*")
	@PostMapping("/score/")
	ResponseEntity<?> submitScore(@RequestBody ScoreDTO score,
	                              @RequestHeader(HttpHeaders.AUTHORIZATION)
	                              String headers) {
		String jwtHeader = headers.split(" ")[1];
		System.out.println("jwtHeader = " + jwtHeader);
		if (null != score) {
			System.out.println("score.username = " + score.username);
			Optional<User> byUsername = userRepo.findByUsername(score.username);
			Score newScore = new Score();
			if (byUsername.isPresent()) {
				newScore.user = byUsername.get();
				newScore.scoreValue = score.score;
				newScore.difficulty = score.difficulty;
				newScore.username = score.username;
				byUsername.get().scores.add(newScore);
				scoreRepo.save(newScore);
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(newScore.scoreValue);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
		}
	}
//		else {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not Authorized");
//		}
}
