package io.airboss.cms.bookings;

import io.airboss.cms.bookings.exceptions.NoAvailableSeatsException;
import io.airboss.cms.flights.Flight;
import io.airboss.cms.flights.FlightRepository;
import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // Mapa para bloquear plazas temporalmente
    private final ConcurrentHashMap<Long, LocalDateTime> seatHoldMap = new ConcurrentHashMap<>();
    
    @Transactional
    public Booking createBooking(Long userId, Long flightId, int numberOfSeats) {
        User user = userRepository.findById(userId)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Flight flight = flightRepository.findById(flightId)
              .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
        
        // Verificar si el vuelo tiene plazas disponibles
        if (flight.getAvailableSeats() < numberOfSeats) {
            throw new NoAvailableSeatsException("No hay suficientes plazas disponibles");
        }
        
        // Bloquear las plazas temporalmente
        seatHoldMap.put(flightId, LocalDateTime.now());
        
        // Reducir plazas disponibles
        flight.setAvailableSeats(flight.getAvailableSeats() - numberOfSeats);
        flightRepository.save(flight);
        
        // Crear la reserva
        Booking booking = new Booking(user, flight, numberOfSeats, BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }
    
    @Transactional
    public void confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
              .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        
        // Liberar el bloqueo de plazas
        seatHoldMap.remove(booking.getFlight().getFlightId());
    }
    
    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
              .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        // Liberar las plazas reservadas
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumberOfSeats());
        flightRepository.save(flight);
        
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        
        // Eliminar el bloqueo de plazas
        seatHoldMap.remove(booking.getFlight().getFlightId());
    }
    
    // Limpiar bloqueos caducados
    @Transactional
    public void clearExpiredHolds() {
        seatHoldMap.forEach((flightId, timestamp) -> {
            if (timestamp.isBefore(LocalDateTime.now().minusMinutes(15))) {
                seatHoldMap.remove(flightId);
                Flight flight = flightRepository.findById(flightId).orElseThrow();
                flight.setAvailableSeats(flight.getAvailableSeats() + flight.getTotalSeats());
                flightRepository.save(flight);
            }
        });
    }
}
