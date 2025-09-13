package com.example.bankcards.security;


import com.example.bankcards.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService {

    @Value("${application.authentication.signing-key}")
    private String SIGNING_KEY;

    @Value("${application.authentication.jwt-expiration-time}")
    private Long EXPIRATION_TIME;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SIGNING_KEY.getBytes());
    }

    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .claim("uid", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUid(String token)  {
        return extractClaim(token, claims -> claims.get("uid", Long.class));
    }

    public String extractRole(String token)  {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        String role = extractRole(token);
        GrantedAuthority grantedAuthority = userDetails.getAuthorities().iterator().next();
        return !isTokenExpired(token)
                && username.equals(userDetails.getUsername())
                && role.equals(grantedAuthority.getAuthority());
    }
}


