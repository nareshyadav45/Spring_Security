package com.security.jwt.sec.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private String SECRET_KEY="secret";
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();	
	}
	
	private <T> T extractClaim(String token,Function<Claims, T> claimsReslover) {
	final	Claims Claims = extractAllClaims(token);
	return claimsReslover.apply(Claims);
		
	}	
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
		
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);	
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());		
	}
	
	
	public String createToken(Map<String, Object> claims,String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*1))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims=new HashMap<>();
		return createToken(claims, userDetails.getUsername());
		
	}
	
	public Boolean validateToken(String token,UserDetails userDetails) 
	{
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
