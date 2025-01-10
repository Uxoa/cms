package io.airboss.cms.security;

import io.airboss.cms.security.auth.JpaUserDetailsService;
import io.airboss.cms.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Value("${api-endpoint}")
    private String apiEndpoint; // Ahora toma la URL desde application.properties
    
    private final JwtService jwtService;
    private final JpaUserDetailsService jpaUserDetailsService;
    
    // Constructor para inyectar dependencias
    public SecurityConfiguration(JwtService jwtService, JpaUserDetailsService jpaUserDetailsService) {
        this.jwtService = jwtService;
        this.jpaUserDetailsService = jpaUserDetailsService;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
              .cors(cors -> cors.disable()) // Desactiva CORS si no lo estás usando
              .csrf(csrf -> csrf.disable()) // Desactiva CSRF para simplificar la autenticación con JWT
              .formLogin(form -> form
                    .loginPage(apiEndpoint + "/login") // Página de login personalizada
                    .permitAll()) // Permite acceso público al login
              .logout(out -> out
                    .logoutUrl(apiEndpoint + "/logout") // URL de logout
                    .invalidateHttpSession(true) // Invalida la sesión al hacer logout
                    .deleteCookies("JSESSIONID")) // Elimina cookies de la sesión
              .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/h2-console/**").permitAll() // Permitir acceso a H2
                    .requestMatchers(apiEndpoint + "/login").permitAll() // Login público
                    .requestMatchers(apiEndpoint + "/public/**").permitAll() // Rutas públicas
                    .requestMatchers(apiEndpoint + "/private/**").hasRole("ADMIN") // Solo admins
                    .anyRequest().authenticated()) // El resto de las rutas requieren autenticación
              .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // Configuración para H2
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Codificador para contraseñas
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Manager de autenticación
    }
    
    
}
