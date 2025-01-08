package io.airboss.cms.repository;

import io.airboss.cms.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    // Métodos personalizados
    Airport findByCode(String code);
}
