package io.airboss.cms.airports;

import org.springframework.stereotype.Service;

import java.util.List;


public interface AirportService {
    Airport getAirportById(String airportId);
    List<Airport> getAllAirports();
}