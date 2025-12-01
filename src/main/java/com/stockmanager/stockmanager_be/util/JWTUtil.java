package com.stockmanager.stockmanager_be.util;

import com.stockmanager.stockmanager_be.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JWTUtil {

    private static final String SECRET = "o7y1Wj8tQ9BYfJx2TUD9b9h8Y2tnWqgxF0aLwZqG6Po=";
    private final long EXPIRATION_TIME = 1000*60*60*24; // 1 hour


    // Key instance generated once from the secret string
    private SecretKey getSigningKey() {
        // Use HS256 algorithm and a secret key that is at least 256 bits (32 bytes)
        byte[] keyBytes = SECRET.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expirationDate = new Date(now + EXPIRATION_TIME);

        return Jwts.builder()
                // 1. Get the claims builder interface and set the values
                .claims(claims)
                .subject(subject) // Sets the 'sub' claim
                .issuedAt(issuedAt) // Sets the 'iat' claim
                .expiration(expirationDate) // Sets the 'exp' claim

                // 2. Sign the token
                .signWith(getSigningKey())

                // 3. Build and serialize
                .compact();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token) // Use parseSignedClaims for JWS (signed tokens)
                .getPayload();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
