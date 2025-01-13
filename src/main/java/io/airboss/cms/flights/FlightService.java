package io.airboss.cms.flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;
    
    public List<Flight> searchFlights(String origin, String destination, LocalDateTime date, int minSeats) {
        return flightRepository.findByOriginAndDestinationAndDepartureTimeAfterAndAvailableSeatsGreaterThan(
              origin, destination, date, minSeats);
    }
    
    @Transactional
    public void updateFlightAvailability(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
              .orElseThrow(() -> new RuntimeException("Flight not found"));
        
        if (flight.getAvailableSeats() == 0 || flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            flight.setAvailable(false);
            flightRepository.save(flight);
        }
    }
}
