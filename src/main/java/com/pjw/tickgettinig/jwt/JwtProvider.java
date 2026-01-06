package com.pjw.tickgettinig.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {
    private final String SECRET;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.SECRET = secret;
    }

    public String getAccessToken(String username) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .signWith(secretKey)
                .claim("id", 0L)
                .id("jwtid")
                .expiration(Date.from(Instant.now().plus(Duration.ofDays(1))))
                .issuedAt(Date.from(Instant.now()))
                .compact();
    }

    public String getRefreshToken(String username) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .signWith(secretKey)
                .claim("id", 0L)
                .id("jwtid")
                .expiration(Date.from(Instant.now().plus(Duration.ofDays(60))))
                .issuedAt(Date.from(Instant.now()))
                .compact();
    }

}
