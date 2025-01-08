package io.airboss.cms;

import io.airboss.cms.entity.Airport;
import io.airboss.cms.entity.Route;
import io.airboss.cms.repository.AirportRepository;
import io.airboss.cms.repository.RouteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CmsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
    
}
