package com.cognixia.jump.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cognixia.jump.service.JwtUserDetailsService;
import com.cognixia.jump.util.JwtUtil;


@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
		throws ServletException, IOException {
		
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String jwt = null;
		String username = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ) {
			
			//scrubs "Bearer " off of string
			jwt = authorizationHeader.substring(7);
			
			username = jwtUtil.extractUsername(jwt);
		}
		
		
		if( username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if( jwtUtil.validateToken(jwt, userDetails) ) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities() );
				
				usernamePasswordAuthenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request) 
						);
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}












