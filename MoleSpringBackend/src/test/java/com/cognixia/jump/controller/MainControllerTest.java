package com.cognixia.jump.controller;

import com.cognixia.jump.model.Score;
import com.cognixia.jump.model.ScoreDTO;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.ScoreRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {
	final String username = "Charles";
	final String password = "b390264a923529cdd2012d033c790ea522effb9b15d7800d3db41effe58fd346";
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	ScoreRepository scoreRepo;

	@Test
	@Order(1)
	@DisplayName("Register User and retrieve JWT")
	void registerUser() throws Exception {
		User user = new User();
		user.username = username;
		user.password = password;
		String userJson = objectMapper
				.writeValueAsString(user);
		MockHttpServletRequestBuilder result = MockMvcRequestBuilders
				.post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson);
		MvcResult mvcResult = mockMvc
				.perform(result)
				.andExpect(status().isCreated())
				.andReturn();
		String[] jwt = mvcResult
				.getResponse()
				.getContentAsString()
				.split(":");


		assert jwt[0].matches(".*jwt.*");
		assert jwt[1].matches(".*ey.*\\..*\\..*");
		assertEquals(mvcResult.getResponse().getStatus(), 201);
	}

	@Test
	@Order(2)
	@DisplayName("Login in and submit a score")
	void loginAndSubmitScore() throws Exception {

		User user = new User();
		user.username = username;
		user.password = password;
		String userJson = objectMapper.writeValueAsString(user);
		MockHttpServletRequestBuilder result = MockMvcRequestBuilders
				.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson);
		MvcResult mvcLoginResult = mockMvc
				.perform(result)
				.andExpect(status().isOk())
				.andReturn();
		String regex = "\\{\"jwt\":\"(.*)\"\\}";
		System.out.println(mvcLoginResult.getResponse().getContentAsString());
		Matcher matcher = Pattern.compile(regex).matcher(mvcLoginResult.getResponse().getContentAsString().trim());
		matcher.matches();
		String jwt = matcher.group(1);

		ScoreDTO scoreDTO = new ScoreDTO(70, "easy", username);
		String scoreJson = objectMapper.writeValueAsString(scoreDTO);


		MockHttpServletRequestBuilder content = MockMvcRequestBuilders
				.post("/score")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + jwt)
				.content(scoreJson);
		MvcResult mvcScoreResult = mockMvc
				.perform(content)
				.andExpect(status().isCreated())
				.andReturn();

		assertEquals(201, mvcScoreResult.getResponse().getStatus());

		MockHttpServletRequestBuilder privateScores = MockMvcRequestBuilders
				.get("/scores/" + username + "/easy")
				.header("Authorization", "Bearer " + jwt);

		MvcResult mvcResult = mockMvc.perform(privateScores).andReturn();

		Score[] scores = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Score[].class);
		Arrays.stream(scores).forEach(score -> {
			assert score.difficulty.equals("easy");
			assert score.username.equals(username);
		});
	}

	@Test
	@Order(3)
	@DisplayName("Retrieve public scores")
	void testRetrievePrivateAndPublicScores() throws Exception {

		MockHttpServletRequestBuilder result = MockMvcRequestBuilders.get("/highscores/easy");
		MvcResult mvcLoginResult = mockMvc.perform(result).andExpect(status().isOk()).andReturn();
		Score[] scores = objectMapper.readValue(mvcLoginResult.getResponse().getContentAsString(), Score[].class);
		assert (scores.length > 0);
		Arrays.stream(scores).forEach(score -> {
			assert score.difficulty.equals("easy");
		});
	}
}
