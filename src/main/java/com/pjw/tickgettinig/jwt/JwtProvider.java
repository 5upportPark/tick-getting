package com.pjw.tickgettinig.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

  private final SecretKey key;

  public JwtProvider(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String getAccessToken(String username, Object id) {
    return Jwts.builder()
        .subject(username)
        .signWith(key)
        .claim("id", id)
        .id(String.valueOf(id))
        .expiration(Date.from(Instant.now().plus(Duration.ofDays(1))))
        .issuedAt(Date.from(Instant.now()))
        .compact();
  }

  public String getRefreshToken(String username) {
    return Jwts.builder()
        .subject(username)
        .signWith(key)
        .claim("id", 0L)
        .id("jwtid")
        .expiration(Date.from(Instant.now().plus(Duration.ofDays(60))))
        .issuedAt(Date.from(Instant.now()))
        .compact();
  }

  public String getUsernameFromToken(String token) {
    Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
      return true;
    } catch (Exception ex) {
      log.error("Invalid JWT token", ex);
    }
    return false;
  }
}
