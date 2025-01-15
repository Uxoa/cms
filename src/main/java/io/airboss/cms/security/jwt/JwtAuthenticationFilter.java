package io.airboss.cms.security.jwt;

import io.airboss.cms.security.auth.JpaUserDetailsService;
import io.airboss.cms.security.auth.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtDecoder jwtDecoder;
    
    @Autowired
    private JpaUserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            
            try {
                Jwt jwt = jwtDecoder.decode(token); // Decodificar el token
                String username = jwt.getSubject();
                
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    
                    UsernamePasswordAuthenticationToken authentication =
                          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                // Manejar tokens inválidos o expirados
                System.out.println("Invalid JWT: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
    
    @RestController
    @RequestMapping(path = "${api-endpoint}/auth")
    public static class AuthController {
        
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
            response.put("token", token);
            response.put("message", "Authentication successful");
            response.put("username", authentication.getName());
            response.put("roles", authentication.getAuthorities().toString());
            
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        
        @PostMapping("/token")
        public ResponseEntity<String> generateToken(Authentication authentication) {
            // Generar un nuevo token basado en la autenticación actual
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(token);
        }
        
     
    }
}
