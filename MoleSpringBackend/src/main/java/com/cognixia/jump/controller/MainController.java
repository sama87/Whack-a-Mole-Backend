package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.JwtRequest;
import com.cognixia.jump.model.JwtResponse;
import com.cognixia.jump.model.Score;
import com.cognixia.jump.model.ScoreDTO;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.ScoreRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin("*")
@RestController
public class MainController {

//	Jwt jwt = new Jwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" + ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
//	String username = "miguel";
	//	String password = "hashed";
//	String password = "U2FsdGVkX18HYydxWuCFBLslkZn2a8R51JgWX4TN2cXahyugFL74IUz5" +
//			"+0a9O8neHKxaWz7Vj9mWrSqJFxX1uLrEcI4FlTbcCfEdq9GdBNL5sXxpq" +
//			"+1BzLMwkrR+u3RktcKTLZJUaos9f3j2xVq8oA==";
//	String password = "b390264a923529cdd2012d033c790ea522effb9b15d7800d3db41effe58fd346";

	@Autowired
	UserRepository userRepo;
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	ScoreRepository scoreRepo;

	ObjectMapper mapper = new ObjectMapper();

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
	@Tag(name = "Create User", description = "Registering new users")
	@Operation(summary = "Registration for New Users", description = "Takes in a new user's username and password and checks whether both usename or password is null. If both username and password is not null, they are saved in the database, and the user is issued a JWT token")
	@ApiResponse(responseCode = "201", description = "A new user was created")
	@ApiResponse(responseCode = "401", description = "one or both credentials were null. User must enter values for username and password")
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
			userRepo.save(new User(user.username, user.password, scores));
			JwtRequest forNewUser = new JwtRequest(user.username, user.password);
			final UserDetails newUser = userDetailsService.loadUserByUsername(forNewUser.getUsername());
			final String jwt = jwtUtil.generateTokens(newUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(jwt));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
		}
	}
	
	@Tag(name = "User scores per level", description = "Scores for each user by level")
	@Operation(summary = "Retrieves score from a registered user in a given level", description = 	"This method checks if the token exists. The security configuration is then authenticated through the security configuration")
	@ApiResponse(responseCode = "200", description = "Returns the ok response that the username verified")
	@ApiResponse(responseCode = "403", description = "Forbidden. the token is invalid/unverified")
	@CrossOrigin("*")
	@GetMapping("/scores/{username}/{level}")
	ResponseEntity<?> score(@PathVariable String username, @PathVariable String level, @RequestHeader(HttpHeaders.AUTHORIZATION) String headers) throws JsonProcessingException {
		// request
		// Authorization header token
		// response 200
		// {[{id: 1, score: 100, dificulty: "easy"},{}]}
		String jwtHeader = headers.split(" ")[1];
		System.out.println("jwtHeader = " + jwtHeader);
//		if (jwtHeader.equals(this.jwt.jwt)) {
		List<Score> byUsername = scoreRepo.findByUsername(username).stream().filter(score -> score.difficulty.equals(level)).toList();
//			byUsername.forEach(score -> {
//				System.out.println("score.scoreValue = " + score.scoreValue);
//
//			});
		return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(byUsername));
	}
//		else {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
//		}
	
	@Tag(name = "All scores by level", description = "Gets all scores by level")
	@Operation(summary = "Gets scores based on levels", description = "The method retrieves information about the scores of a given level. It gets all the scores from the database which prints the high scores of a given level; retrieves high scores for easy, medium or hard")
	@ApiResponse(responseCode = "200", description = "Data found in score table and prints all high scores of the difficulty")
	@CrossOrigin("*")
	@GetMapping("/highscores/{level}")
	ResponseEntity<?> highscore(@PathVariable String level) throws JsonProcessingException {
		// nothing needed
		List<Score> scores = scoreRepo.findAll().stream().filter(score -> score.difficulty.equals(level)).toList();
		return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(scores));
	}
	
	@Tag(name = "New Score for user", description = "retrieve new achieved score for user")
	@Operation(summary = "Gets scores of a given user retrieved from JWT", description = "The Score DTO(Data Transfer Object) is an intermediary which holds the value of the score while checking the JWT token. The score then will be passed to the user in the database and pass the new score. The user is saved with a new score and")
	@ApiResponse(responseCode = "201", description = "User score is submitted")
	@ApiResponse(responseCode = "400", description = "Bad Request")
	@CrossOrigin("*")
	@PostMapping("/score")
	ResponseEntity<?> submitScore(@RequestBody ScoreDTO score, @RequestHeader(HttpHeaders.AUTHORIZATION) String headers) {
		String jwtHeader = headers.split(" ")[1];
		System.out.println("jwtHeader = " + jwtHeader);
//		if (jwtHeader.equals(this.jwt.jwt)) {
		System.out.println("score = " + score.difficulty);
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
