package com.jaw.auth;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {

	private static final String USER_ID = "userId";

	private final Key key;

	public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public String encode(Long userId) {
		validateUserId(userId);
		return Jwts.builder()
			.claim(USER_ID, userId)
			.signWith(key)
			.compact();
	}

	private void validateUserId(Long userId) {
		if (userId == null || userId <= 0) {
			throw new IllegalArgumentException();
		}
	}

	public Long decode(String token) {
		validateToken(token);
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.get(USER_ID, Long.class);
		} catch (SignatureException | MalformedJwtException e) {
			throw new IllegalArgumentException();
		}
	}

	private void validateToken(String token) {
		if (token == null || token.isBlank()) {
			throw new IllegalArgumentException();
		}
	}
}
