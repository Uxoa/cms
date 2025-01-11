package io.airboss.cms.flights;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginAndDestinationAndDepartureTimeAfterAndAvailableSeatsGreaterThan(
          String origin, String destination, LocalDateTime date, int minSeats);
}
