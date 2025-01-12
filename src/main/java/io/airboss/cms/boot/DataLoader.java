package io.airboss.cms.boot;

import io.airboss.cms.airports.Airport;
import io.airboss.cms.airports.AirportRepository;
import io.airboss.cms.bookings.Booking;
import io.airboss.cms.bookings.BookingRepository;
import io.airboss.cms.bookings.BookingStatus;
import io.airboss.cms.flights.Flight;
import io.airboss.cms.flights.FlightRepository;
import io.airboss.cms.profiles.Profile;
import io.airboss.cms.profiles.ProfileRepository;
import io.airboss.cms.roles.Role;
import io.airboss.cms.roles.RoleRepository;
import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Transactional
public class DataLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    
    private static final String AIRPORTS_FILE = "csv/airports.csv";
    private static final String FLIGHTS_FILE = "csv/flights.csv";
    private static final String USERS_FILE = "csv/users.csv";
    private static final String BOOKINGS_FILE = "csv/bookings.csv";
    private static final String ROLES_FILE = "csv/roles.csv";
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private AirportRepository airportRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    
    @Bean
    @Order(1)
    CommandLineRunner loadRoles() {
        return args -> loadFromCsv(ROLES_FILE, values ->
              new Role(values[0]), roleRepository);
        
    }
    
    @Bean
    @Order(2)
    CommandLineRunner loadAirports() {
        return args -> loadFromCsv(AIRPORTS_FILE, values ->
              new Airport(values[2], values[1], values[0]), airportRepository);
    }
    
    @Bean
    @Order(3)
    CommandLineRunner loadFlights() {
        return args -> loadFromCsv(FLIGHTS_FILE, values ->
              new Flight(
                    values[0], // Origin
                    values[1], // Destination
                    values[2], // Departure Time
                    Integer.parseInt(values[3]), // Total Seats
                    Integer.parseInt(values[4]), // Available Seats
                    Boolean.parseBoolean(values[5]), // Is Available
                    values[6] // Airline Name
              ), flightRepository);
    }
    
    @Bean
    @Order(4)
    CommandLineRunner loadUsersAndProfiles() {
        return args -> loadFromCsv(USERS_FILE, values -> {
            // Verificar si el usuario ya existe
            Optional<User> existingUser = userRepository.findByEmail(values[1]);
            if (existingUser.isPresent()) {
                logger.warn("Usuario con email {} ya existe. Omitiendo.", values[1]);
                return null;
            }
            
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            
            // Crear nuevo usuario
            User user = new User();
            user.setUsername(values[0]);
            user.setEmail(values[1]);
            user.setPassword(encoder.encode(values[2]));
            
            // Crear perfil
            Profile profile = new Profile();
            profile.setName(values[3]);
            profile.setLastName(values[4]);
            profile.setMobile(Long.parseLong(values[5]));
            profile.setProfileImage(values[6]);
            profile.setRegistrationDate(LocalDateTime.now());
            profile.setLastLogin(LocalDateTime.now());
            profile.setUser(user);
            
            // Guardar perfil
            profileRepository.save(profile);
            
            // Asignar roles
            List<Role> roles = Arrays.stream(values[7].split(","))
                                     .map(roleRepository::findByName)
                                     .filter(Optional::isPresent)
                                     .map(Optional::get)
                                     .collect(Collectors.toList());
            user.setRoles(roles);
            
            // Guardar usuario con perfil (esto automáticamente guarda las relaciones en user_roles)
            userRepository.save(user);
            
            logger.info("Usuario {} creado con roles: {}", user.getUsername(), roles.stream().map(Role::getName).toList());
            return user;
        }, userRepository);
    }
    
    @Bean
    @Order(5)
    CommandLineRunner loadBookings() {
        return args -> loadFromCsv(BOOKINGS_FILE, values -> {
            // Buscar vuelo
            Long flightId = Long.parseLong(values[1]);
            var flight = flightRepository.findById(flightId).orElse(null);
            if (flight == null) {
                logger.warn("Vuelo no encontrado con ID: {}. Línea omitida.", flightId);
                return null; // Omite esta línea
            }
            
            // Buscar usuario
            Long userId = Long.parseLong(values[2]);
            var user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                logger.warn("Usuario no encontrado con ID: {}. Línea omitida.", userId);
                return null; // Omite esta línea
            }
            
            // Crear y devolver la reserva
            Booking booking = new Booking();
            booking.setFlight(flight);
            booking.setUser(user);
            booking.setStatus(BookingStatus.valueOf(values[3])); // Estado de la reserva
            return booking;
        }, bookingRepository);
    }
    
    private <T> void loadFromCsv(String filePath, Function<String[], T> mapper, CrudRepository<T, ?> repository) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            try (Stream<String> lines = Files.lines(Paths.get(resource.getFile().toURI()))) {
                lines.skip(1) // Saltar encabezados
                      .map(line -> {
                          try {
                              String[] values = line.split(",");
                              return mapper.apply(values);
                          } catch (Exception e) {
                              logger.error("Error procesando línea: {}", line, e);
                              return null;
                          }
                      })
                      .filter(entity -> entity != null)
                      .forEach(repository::save);
                logger.info("Archivo {} cargado correctamente.", filePath);
            }
        } catch (Exception e) {
            logger.error("Error leyendo archivo {}: {}", filePath, e.getMessage());
        }
    }
}