package com.CN.Gym.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtAuthenticationHelper jwtAuthenticationHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader!=null && authHeader.startsWith("Bearer")) {
			String token = authHeader.substring(7);
			String username = jwtAuthenticationHelper.getUsernameFromToken(token);
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				if(!jwtAuthenticationHelper.isExpired(token)) {
					UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(token, null, userDetails.getAuthorities());
					userAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
				}
				
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
