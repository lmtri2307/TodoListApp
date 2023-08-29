package com.example.todolist.utils;

import com.example.todolist.domain.User.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JWTUtils {
    private final String JWT_SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final long JWT_EXPIRATION = 604800000L;
    private final SignatureAlgorithm JWT_ALGORITHM = SignatureAlgorithm.HS256;

    public String generateToken(UserDTO userDTO){
        Date current = new Date();
        Date expiredDate = new Date(current.getTime() + JWT_EXPIRATION);

        return Jwts
                .builder()
                .setSubject(userDTO.getEmail())
                .setIssuedAt(current)
                .setExpiration(expiredDate)
                .signWith(getSignKey(), JWT_ALGORITHM)
                .compact();
    }

    public String getSubject(String token){
        Claims claims = getClaims(token);

        return claims.getSubject();
    }

    public boolean verifyToken(String token){
        Claims claims = getClaims(token);
        Instant now = Instant.now();
        Date exp = claims.getExpiration();
        return exp.after(Date.from(now));
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
