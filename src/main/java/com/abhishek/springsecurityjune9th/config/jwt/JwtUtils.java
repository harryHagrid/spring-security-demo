package com.abhishek.springsecurityjune9th.config.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * generate the JWT token
 *
 */

@Component
public class JwtUtils {
	
	@Value("${app.jwtsecret}")
	private String secret;

	private static final long TOKEN_VALIDITY = 600;
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims:: getSubject);
	}
	
	public Date getIssueAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims:: getIssuedAt);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims:: getExpiration);
	}
	
	
	/**
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return claims
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver ) {
		
		final Claims claims = getAllClaimsFromToken(token);
		
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
	
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * 
	 * @param userDetails
	 * @return token
	 */
	public String generateToken(UserDetails userDetails) {
		
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
		
	}
	
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
		
	}
	
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		
		String username = getUsernameFromToken(token);
		
		return (username.equals(userDetails.getUsername()) && (!isTokenExpired(token)));
	}

	private boolean isTokenExpired(String token) {
		
		Date expiration = getExpirationDateFromToken(token);
		System.out.println(expiration);
		
		return expiration.before(new Date());
	}
}
