package io.airboss.cms.security;

import io.airboss.cms.security.auth.JpaUserDetailsService;
import io.airboss.cms.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
              .cors(withDefaults()) // Configuración CORS por defecto
              .csrf(csrf -> csrf.disable()) // Desactivar CSRF
              .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/login").permitAll() // Permitir acceso público al login
                    .anyRequest().authenticated()) // Proteger todos los demás endpoints
              .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añadir JwtAuthenticationFilter
              .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Sin estado para JWT
        
        return http.build();
    };
}
