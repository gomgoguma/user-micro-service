package com.example.user.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Configuration
public class JwtConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final Key key;

    public JwtConfig() {
        byte[] secretKeyBytes = Base64.getDecoder().decode("efwkjfejwlfjsdkfjhjkujhgfhnsbfejfuyfgshjkdfgweukfhjkwefgwjkhefgdjsnfjhksdfg");
        key = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String createAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 10))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+  1000 * 60 * 60 * 24 * 7))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
