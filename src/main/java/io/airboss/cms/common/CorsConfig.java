package io.airboss.cms.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Configuración de CORS
        config.setAllowCredentials(true); // Permitir cookies y credenciales
        config.setAllowedOrigins(Arrays.asList("http://localhost:3306", "\"http://localhost:8080")); // Dominios permitidos
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // Headers permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        
        // Aplicar configuración a todas las rutas que coincidan con "/api/**"
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
