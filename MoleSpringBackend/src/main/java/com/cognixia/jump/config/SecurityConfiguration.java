package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtFilter;

@Configuration
public class SecurityConfiguration {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtFilter jwtRequestFilter;
	
	@Bean
	protected UserDetailsService userDetailsService() {
		
		return userDetailsService;
	}
	
	@Bean
	protected SecurityFilterChain filterChain( HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/hello").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class );
		
		return http.build();
	}
	
	

}
