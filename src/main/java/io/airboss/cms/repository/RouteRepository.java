package io.airboss.cms.repository;

import io.airboss.cms.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
    // Métodos personalizados
    Route findByOriginAirportCodeAndDestinationAirportCode(String originAirportCode, String destinationAirportCode);
    
}
