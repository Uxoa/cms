package io.airboss.cms.config;

import io.airboss.cms.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Asegúrate de que Spring pueda encontrar este bean
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
              .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/admin/**").hasRole("ADMIN") // Solo para ADMIN
                    .requestMatchers("/user/**").hasRole("USER")   // Solo para USER
                    .anyRequest().authenticated()                 // Resto debe estar autenticado
              )
              .headers(headers -> headers
                    .cacheControl(cache -> cache.disable()) // Deshabilitar caché
              )
              .httpBasic(Customizer.withDefaults()) // Habilitar Basic Auth
              .logout(logout -> logout
                    .logoutUrl("/logout") // URL para logout
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Responder con 401
                        response.getWriter().write("Has cerrado sesión correctamente."); // Mensaje de logout
                    })
              );
        
        return http.build();
    }

    
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }
}
