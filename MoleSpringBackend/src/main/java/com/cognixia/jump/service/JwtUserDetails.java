package com.cognixia.jump.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognixia.jump.model.User;

import io.swagger.v3.oas.annotations.media.Schema;

public class JwtUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1l;
	
	@Schema(description = "Username for token")
	private String username;
	
	@Schema(description = "Password for token")
	private String password;
	private boolean enabled;
	private List<GrantedAuthority> authorities;
	
	public JwtUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.enabled = user.isEnabled(); 
		
		//for now there are only users. May need refactoring if we include admins later
		this.authorities = Arrays.asList(new SimpleGrantedAuthority("user") );
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
