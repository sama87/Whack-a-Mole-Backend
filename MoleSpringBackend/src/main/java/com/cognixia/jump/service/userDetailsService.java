package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userDetailsService implements UserDetailsService {
	
	//@Autowired
	//repository repo
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userFound = repo.//Insert method used
				
		if(userFound.isEmpty() ) {
			throw new UsernameNotFoundException(username);
		}
		
		return new //UserDetails(userFound.get() );
	}

}
