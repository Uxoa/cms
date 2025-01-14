package io.airboss.cms.security.jwt;

import io.airboss.cms.roles.Role;
import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private long expiration;
    
    @Autowired
    private UserRepository userRepository;
    
    // Generar token con username y roles
    public String generateToken(String username) {
        System.out.println("Buscando usuario con username: " + username);
        
        User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new RuntimeException("User not found"));
        
        System.out.println("Usuario encontrado: " + user);
        
        List<String> roles = user.getRoles().stream().map(Role::getName).toList();
        
        return Jwts.builder()
              .setSubject(username)
              .claim("roles", roles)
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
              .signWith(SignatureAlgorithm.HS256, secret)
              .compact();
    }
    
    // Extraer username del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    // Extraer fecha de expiración del token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    // Validar el token
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
    
    // Verificar si el token está expirado
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    // Extraer una reclamación específica
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    // Extraer todas las reclamaciones
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
              .setSigningKey(secret)
              .parseClaimsJws(token)
              .getBody();
    }
}
