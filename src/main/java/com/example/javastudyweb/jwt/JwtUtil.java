package com.example.javastudyweb.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final long EXPIRATION = 1000 * 60 * 60; // 1시간

    // 토큰 만들기
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    // 토큰에서 username 꺼내기
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // 토큰 유효한지 확인
    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // 토큰 해석 및 내부 데이터 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}