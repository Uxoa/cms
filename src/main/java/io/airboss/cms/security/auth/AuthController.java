package io.airboss.cms.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "${api-endpoint}/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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
        
        // Establecer el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Generar el token JWT
        String token = tokenService.generateToken(authentication);
        
        // Crear respuesta con el token
        Map<String, String> response = new HashMap<>();
        response.put("message", "Authentication successful");
        response.put("username", authentication.getName());
        response.put("roles", authentication.getAuthorities().toString());
        response.put("token", token);
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PostMapping("/token")
    public ResponseEntity<String> generateToken(Authentication authentication) {
        // Generar un nuevo token basado en la autenticación actual
        String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(token);
    }
}


