package io.airboss.cms;

import io.airboss.cms.entity.Airport;
import io.airboss.cms.entity.Route;
import io.airboss.cms.entity.User;
import io.airboss.cms.repository.AirportRepository;
import io.airboss.cms.repository.RouteRepository;
import io.airboss.cms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class CmsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
    
    
    @Bean
    public CommandLineRunner run(RouteRepository routeRepository, AirportRepository airportRepository) {
        return args -> {
            // Obtener aeropuertos existentes
            Airport origin = airportRepository.findByCode("INTL");
            Airport destination = airportRepository.findByCode("REGNL");
            
            // Crear una ruta de prueba
            Route route = new Route();
            route.setOriginAirport(origin);
            route.setDestinationAirport(destination);
            route.setEstimatedDuration(120);
            route.setFrequency("Diaria");
            route.setActive(true);
            
            // Guardar la ruta en la base de datos
            routeRepository.save(route);
            
            // Mostrar la ruta insertada
            System.out.println("Ruta insertada: " + route);
        };
    }
    
    
    
    
}
