package com.resolver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

    private static final long EXPIRATION_TIME = 900000; // 15 minutes

    // Generate JWT Token
    public static String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    // Extract username
    public static String extractUsername(String token) {

        return getClaims(token).getSubject();
    }

    // Extract role
    public static String extractRole(String token) {

        return getClaims(token).get("role", String.class);
    }

    // Validate token
    public static boolean validateToken(String token) {

        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extract all claims
    private static Claims getClaims(String token) {

        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}