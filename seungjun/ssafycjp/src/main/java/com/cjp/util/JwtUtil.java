package com.cjp.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	// 32바이트 이상으로 설정해야 한다!
	private static final String key = "SSAFYSSAFYSSAFYSSAFYSSAFYSSAFYSSAFYSSAFY";
	private SecretKey secretkey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	// 만료 수명 1시간으로 설정 (1000*60*60)
	private static final long EXPIRATION_TIME = 1000*60*60;
	
	// JWT 생성
	public String createToken(String id) {
		return Jwts.builder()
				// 헤더 설정
//				.header().add("typ", "JWT").and()
				
				// 페이로드 설정 (JWT Claims)
				// 주제
				.setSubject(id)
				// 만료 시간 설정. 현재 시간 + 수명
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				// 발행 시간 설정
				.setIssuedAt(new Date())
				
				// 서명 알고리즘과 비밀 키 설정
				.signWith(secretkey, SignatureAlgorithm.HS256)
				// JWT 문자열 생성
				.compact();
	}
	
	// JWT 검증
	public boolean validate(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretkey)
				.build()
				.parseClaimsJws(token);
			// 유효한 토큰
			System.out.println("유효한 토큰!");
			return true;
		} catch (Exception e) {
			// 유효하지 않은 토큰
			System.out.println(e.getMessage());
			System.out.println("유효하지 않은 토큰");
			return false;
		}
	}
	
	// 토큰에서 정보 추출
	public String getIdFromToken(String token) {
		System.out.println("토큰에서 정보 추출 들어왔어!");
		if(token != null) {
			String[] input = token.split(" ");
			token = input[1];			
		}
		Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(secretkey)
				.build()
				.parseClaimsJws(token);
		System.out.println(claims.getBody().getSubject());
		return claims.getBody().getSubject();
	}
}