package io.airboss.cms.airports;

import java.util.List;

import io.airboss.cms.airports.exceptions.AirportNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
public class AirportServiceImpl implements AirportService {
    
    @Autowired
    private AirportRepository airportRepo;
    
    @Override
    public Airport getAirportById(String airportId) {
        return airportRepo.findById(airportId).orElseThrow(() -> new AirportNotFoundException(airportId));
    }
    
    @Override
    public List<Airport> getAllAirports() {
        return airportRepo.findAll();
    }
    
}