package io.airboss.cms.security;

import io.airboss.cms.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        
        try {
            // Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(email, password)
            );
            
            // Cargar los detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            
            // Generar el token JWT
            String token = jwtService.generateToken(userDetails);
            
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(403).body(Map.of("error", "Credenciales inválidas."));
        }
    }
}
