package com.CN.Gym.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthenticationHelper {
	
	private String secret = "T9R7r5awbiHNYx3skeK2c9IDMhQnlvnvCmDd1ckIG8ZOurcvhu0Wl52lMK5kEso9";
	
	private final long TOKEN_EXPIRES_IN = 15*60;
	
	public String getUsernameFromToken(String jwtToken) {
		Claims claims = this.getClaimsFromToken(jwtToken);
		return claims.getSubject();
	}
	
	private Claims getClaimsFromToken(String jwtToken) {
		Claims claims = Jwts.parserBuilder().setSigningKey(secret.getBytes())
							.build().parseClaimsJws(jwtToken).getBody();
		
		return claims;
	}
	
	public boolean isExpired(String jwtToken) {
		Claims claims = this.getClaimsFromToken(jwtToken);
		return claims.getExpiration().before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		
		Map<String, Object> claims = new HashMap<String, Object>();
		
		String token = Jwts.builder().setClaims(claims)
		.setSubject(userDetails.getUsername())
		.setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis()+TOKEN_EXPIRES_IN*1000))
		.signWith(new SecretKeySpec(this.secret.getBytes(), SignatureAlgorithm.HS512.getJcaName()), SignatureAlgorithm.HS512)
		.compact();
		
		return token;
	}

}
