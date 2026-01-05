package com.pjw.tickgettinig.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class JwtProvider {

    public static String getAccessToken(String username, String password) {
        SecretKey secretKey = Keys.builder(new SecretKeySpec("secretkey".getBytes(StandardCharsets.UTF_8), "HS256"))
                        .build();
        return Jwts.builder()
                .subject(username)
                .signWith(secretKey)
                .claim("id", 0L)
                .id("jwtid")
                .expiration(Date.from(Instant.now().plus(Duration.ofDays(1))))
                .issuedAt(Date.from(Instant.now()))
                .compact();
    }

    public static String getRefreshToken(String username, String password) {
        SecretKey secretKey = Keys.builder(new SecretKeySpec("secretkey".getBytes(StandardCharsets.UTF_8), "HS256"))
                .build();
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
