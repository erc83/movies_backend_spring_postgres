package com.avanzado.movies_backend.services;

import com.avanzado.movies_backend.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.security.movies_backend.secret-key}")
    private String secretKey;
    @Value("${application.security.movies_backend.expiration}")
    private long jwtExpiration;
    @Value("${application.security.movies_backend.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    public String generateToken(final User user) {
        return buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(final User user) {
        String token = buildToken(user, refreshExpiration);
        System.out.println("Generated Token: " + token);
        return token;
    }

    private String buildToken(final User user, final long expiration) {
        return Jwts.builder()
                .id(user.getId().toString())
                .subject(user.getEmail())
                .claims(Map.of("name", user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();   
    }

    private SecretKey getSignInKey() {
        //byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        //return new SecretKeySpec(keyBytes,0,keyBytes.length, secretKey);

        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
