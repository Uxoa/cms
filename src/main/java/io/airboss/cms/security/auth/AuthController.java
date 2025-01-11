package io.airboss.cms.security.auth;

import io.airboss.cms.security.jwt.JwtTokenProvider;
import io.airboss.cms.security.jwt.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
              )
        );
        
        // Generar token JWT
        String token = jwtTokenProvider.generateToken(authentication.getName());
        
        // Crear respuesta con el token
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Authentication successful");
        
        return ResponseEntity.ok(response);
    }
}
