package io.airboss.cms.airports;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    // Métodos personalizados
    Airport findByCode(String code);
}
