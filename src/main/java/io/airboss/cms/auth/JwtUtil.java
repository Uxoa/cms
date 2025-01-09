// JwtUtil.java
package io.airboss.cms.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public String generateToken(String username, String role) {
        return Jwts.builder()
              .setSubject(username)
              .claim("role", role)
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
              .signWith(SECRET_KEY)
              .compact();
    }
    
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY)
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
    }
    
    public String extractRole(String token) {
        return Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY)
              .build()
              .parseClaimsJws(token)
              .getBody()
              .get("role", String.class);
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }
}
