package io.airboss.cms.flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    
    @GetMapping("/search")
    public List<Flight> searchFlights(
          @RequestParam String origin,
          @RequestParam String destination,
          @RequestParam String date,
          @RequestParam int seats) {
        
        LocalDateTime departureDate = LocalDateTime.parse(date);
        return flightService.searchFlights(origin, destination, departureDate, seats);
    }
    
    @PutMapping("/{id}/update")
    public void updateFlightAvailability(@PathVariable Long id) {
        flightService.updateFlightAvailability(id);
    }
}
