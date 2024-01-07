package com.CN.Gym.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.CN.Gym.jwt.JwtAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity
public class JwtSecurityConfig {
	
//	@Autowired
//	private JwtAuthenticationFilter jwtAuthenticationFilter;
//	
//	@Bean
//	public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.csrf().disable()
//			.authorizeHttpRequests()
//				.antMatchers("/user/register", "/auth/login").permitAll()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		
//		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//		return httpSecurity.build();
//	}

}
