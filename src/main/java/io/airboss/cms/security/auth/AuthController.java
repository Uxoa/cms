package io.airboss.cms.security.auth;

import io.airboss.cms.security.jwt.JwtUtil;
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
@RequestMapping(path = "${api-endpoint}/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtUtil jwtUtil;
    
    private final TokenService tokenService;
    

    @Autowired
    public AuthController(TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Intentando autenticar usuario: " + loginRequest.getUsername());
        System.out.println("Contraseña recibida: " + loginRequest.getPassword());
        
        Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
              )
        );
        
        
        
        
        
    
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        
        return ResponseEntity.ok(response);
    }
    
    public AuthController(TokenService tokenService) {
            this.tokenService = tokenService;
        }
        
        @PostMapping("/token")
        public String token(Authentication authentication) {
            return tokenService.generateToken(authentication);
        }
        
        
    }
}
