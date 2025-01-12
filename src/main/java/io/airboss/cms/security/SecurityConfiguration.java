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
              .cors(withDefaults())
              .csrf(csrf -> csrf.disable())
              .authorizeHttpRequests(auth -> auth
                    // Endpoints públicos
                    .requestMatchers("/api/login").permitAll()
                    
                    // Endpoints relacionados con Users
                    .requestMatchers(HttpMethod.POST, "/user/add").hasRole("ADMIN") // Crear usuarios, solo ADMIN
                    .requestMatchers(HttpMethod.GET, "/api/user/{userId}").hasAnyRole("USER", "ADMIN") // Ver usuario por ID (USER y ADMIN)
                    .requestMatchers(HttpMethod.DELETE, "/api/user/{userId}").hasRole("ADMIN") // Eliminar usuarios, solo ADMIN
                    .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN") // Ver todos los usuarios, solo ADMIN
                    
                    // Endpoints relacionados con Flights
                    .requestMatchers(HttpMethod.GET, "/api/flights").hasAnyRole("USER", "ADMIN") // Ver vuelos disponibles
                    .requestMatchers(HttpMethod.GET, "/api/flights/{flightId}").hasAnyRole("USER", "ADMIN") // Ver detalles de un vuelo
                    .requestMatchers(HttpMethod.POST, "/api/flights").hasRole("ADMIN") // Crear vuelos, solo ADMIN
                    .requestMatchers(HttpMethod.PUT, "/api/flights/{flightId}").hasRole("ADMIN") // Actualizar vuelos, solo ADMIN
                    .requestMatchers(HttpMethod.DELETE, "/api/flights/{flightId}").hasRole("ADMIN") // Eliminar vuelos, solo ADMIN
                    
                    // Endpoints relacionados con Airports
                    .requestMatchers(HttpMethod.GET, "/api/airports").hasAnyRole("USER", "ADMIN") // Ver aeropuertos disponibles
                    .requestMatchers(HttpMethod.GET, "/api/airports/{airportId}").hasAnyRole("USER", "ADMIN") // Ver detalles de un aeropuerto
                    .requestMatchers(HttpMethod.POST, "/api/airports").hasRole("ADMIN") // Crear aeropuertos, solo ADMIN
                    .requestMatchers(HttpMethod.PUT, "/api/airports/{airportId}").hasRole("ADMIN") // Actualizar aeropuertos, solo ADMIN
                    .requestMatchers(HttpMethod.DELETE, "/api/airports/{airportId}").hasRole("ADMIN") // Eliminar aeropuertos, solo ADMIN
                    
                    // Endpoints relacionados con Bookings
                    .requestMatchers(HttpMethod.GET, "/api/bookings/me").hasRole("USER") // Ver reservas del usuario autenticado
                    .requestMatchers(HttpMethod.GET, "/api/bookings").hasRole("ADMIN") // Ver todas las reservas, solo ADMIN
                    .requestMatchers(HttpMethod.POST, "/api/bookings/**").hasRole("USER") // Crear reservas, solo USER
                    
                    // Cualquier otra solicitud requiere autenticación
                    .anyRequest().authenticated())
              .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
