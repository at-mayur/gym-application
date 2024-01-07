package com.CN.Gym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.CN.Gym.dto.JwtRequest;
import com.CN.Gym.dto.JwtResponse;
import com.CN.Gym.jwt.JwtAuthenticationHelper;

@Service
public class AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtAuthenticationHelper jwtAuthenticationHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;

	public JwtResponse login(JwtRequest jwtRequest) {
		this.doAuthentication(jwtRequest.getUsername(), jwtRequest.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String jwtToken = jwtAuthenticationHelper.generateToken(userDetails);
		
		return JwtResponse.builder().jwtToken(jwtToken).build();
	}
	
	private void doAuthentication(String username, String password) {
		UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			
			authenticationManager.authenticate(userAuthenticationToken);
			
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username or Password");
		}
	}

}
