package io.airboss.cms.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    
    // Genera una clave segura de 256 bits
    private static final String SECRET_KEY_BASE64 = "M2NmYTc2ZWYxNDkzN2MxYzBlYTUxOWY4ZmMwNTdhODBmY2QwNGE3NDIwZjhlOGJjZDBhNzU2N2MyNzJlMDA3Yg=="; // Ejemplo de clave en Base64
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(SECRET_KEY_BASE64));
    
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
        return Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY)
              .build()
              .parseClaimsJws(token)
              .getBody();
    }
    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    public Boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
    
    public String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
              .setClaims(claims)
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
              .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
              .compact();
    }
    
    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }
    
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }
    
}