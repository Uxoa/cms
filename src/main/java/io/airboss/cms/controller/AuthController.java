package io.airboss.cms.controller;

import io.airboss.cms.entity.User;
import io.airboss.cms.repository.UserRepository;
import io.airboss.cms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Endpoint para iniciar sesión.
     * Devuelve el token en una cookie HTTP y un mensaje de éxito.
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        
        User user = userOptional.get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        
        // Crear una cookie HTTP para el token
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
              .httpOnly(true) // Evitar acceso a través de JavaScript
              .secure(false) // Cambiar a true en producción con HTTPS
              .path("/")
              .maxAge(24 * 60 * 60) // Expira en 24 horas
              .build();
        
        return ResponseEntity.ok()
              .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
              .body("Inicio de sesión exitoso");
    }
    
    /**
     * Página de inicio de sesión (solo para vistas Thymeleaf).
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login"; // Renderizará la plantilla templates/login.html
    }
    
    /**
     * Clase interna para representar la solicitud de inicio de sesión.
     */
    public static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
