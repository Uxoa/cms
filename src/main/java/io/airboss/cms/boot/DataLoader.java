//package io.airboss.cms.boot;
//
//import io.airboss.cms.flights.Flight;
//import io.airboss.cms.flights.FlightRepository;
//import io.jsonwebtoken.io.IOException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import io.airboss.cms.airports.Airport;
//import io.airboss.cms.airports.AirportRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.cglib.core.internal.Function;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.stream.Stream;
//
//@Component
//@Transactional
//public class DataLoader {
//    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
//    private static final String airportsFile = "csv/airports_with_iata_code.csv";
//    private static final String flightsFile = "csv/flights.csv";
//
//
//    @Autowired
//    private ResourceLoader resourceLoader;
//
//    @Autowired
//    private AirportRepository airportRepository;
//
//    @Autowired
//    private FlightRepository flightRepository;
//
//
//    @Bean
//    @Order(1)
//    CommandLineRunner loadAirports(AirportRepository airportRepository) {
//        return (args) -> {
//            loadFromCsv(resourceLoader, airportsFile, v -> new Airport(v[3], v[0], v[2]),
//                    airportRepository);
//        };
//    }
//
//    @Bean
//    @Order(1)
//    CommandLineRunner loadFlights(FlightRepository flightRepository) {
//        return (args) -> {
//            loadFromCsv(resourceLoader, flightsFile, v -> new Flight(v[0], v[1], v[2], Integer.parseInt(v[3]), Integer.parseInt(v[4]), Boolean.parseBoolean(v[5])),
//                    flightRepository);
//        };
//    }
//
//    public static void loadFromCsv(ResourceLoader resourceLoader, String sourceCsvFile,
//                                   Function<String[], Object> objectMapper, CrudRepository repo) {
//        logger.debug("++++++++++++++ Loading " + sourceCsvFile + " ..........");
//
//        Resource resource = resourceLoader.getResource("classpath:" + sourceCsvFile);
//
//        try (Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()))) {
//            stream.forEach(line -> {
//                logger.debug("++++++++++++++" + line);
//                try {
//                    String[] values = line.split(",");
//                    Object entity = objectMapper.apply(values);
//                    repo.save(entity);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (java.io.IOException e) {
//            throw new RuntimeException(e);
//        }
//        logger.debug("++++++++++++++ Loading " + sourceCsvFile + " DONE !");
//
//    }
//
//    public AirportRepository getAirportRepository() {
//        return airportRepository;
//    }
//
//    public void setAirportRepository(AirportRepository airportRepository) {
//        this.airportRepository = airportRepository;
//    }
//
//    public FlightRepository getFlightRepository() {
//        return flightRepository;
//    }
//
//    public void setFlightRepository(FlightRepository flightRepository) {
//        this.flightRepository = flightRepository;
//    }
//}