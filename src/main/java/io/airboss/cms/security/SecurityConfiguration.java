package io.airboss.cms.security;

import io.airboss.cms.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    
    @Value("${api-endpoint}")
    String endpoint;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
              .cors(withDefaults()) // Configuración CORS por defecto
              .csrf(csrf -> csrf.disable()) // Desactivar CSRF
              .authorizeHttpRequests(auth -> auth // Configurar las reglas de autorización
                    .requestMatchers(endpoint + "/login").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(endpoint).permitAll() // Principio de mínimos privilegios
                    .requestMatchers(endpoint + "/public").permitAll()
                    .requestMatchers(endpoint + "/private").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, endpoint + "/countries").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, endpoint + "/countries").hasRole("ADMIN")
                    .anyRequest().authenticated()
              )
              .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Añadir el filtro JWT
        
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
