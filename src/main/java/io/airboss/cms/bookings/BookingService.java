package io.airboss.cms.bookings;

import io.airboss.cms.bookings.exceptions.NoAvailableSeatsException;
import io.airboss.cms.flights.Flight;
import io.airboss.cms.flights.FlightRepository;
import io.airboss.cms.users.User;
import io.airboss.cms.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Validar usuario y vuelo
        User user = userRepository.findById(userId)
              .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Flight flight = flightRepository.findById(flightId)
              .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));
        
        // Verificar disponibilidad de asientos
        if (flight.getAvailableSeats() < numberOfSeats) {
            throw new NoAvailableSeatsException("No hay suficientes plazas disponibles");
        }
        
        // Bloquear temporalmente las plazas
        seatHoldMap.put(flightId, LocalDateTime.now());
        
        // Reducir plazas disponibles y guardar el vuelo actualizado
        flight.setAvailableSeats(flight.getAvailableSeats() - numberOfSeats);
        flightRepository.save(flight);
        
        // Crear y guardar la reserva
        Booking booking = new Booking(user, flight, numberOfSeats, BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }
    
    @Transactional
    public void confirmBooking(Long bookingId) {
        // Buscar la reserva
        Booking booking = bookingRepository.findById(bookingId)
              .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        // Cambiar estado a CONFIRMED y guardar
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        
        // Eliminar bloqueo de plazas
        seatHoldMap.remove(booking.getFlight().getFlightId());
    }
    
    @Transactional
    public void cancelBooking(Long bookingId) {
        // Buscar la reserva
        Booking booking = bookingRepository.findById(bookingId)
              .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        // Devolver plazas al vuelo
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumberOfSeats());
        flightRepository.save(flight);
        
        // Cambiar estado a CANCELLED y guardar
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        
        // Eliminar bloqueo de plazas
        seatHoldMap.remove(booking.getFlight().getFlightId());
    }
    
    // Limpieza de bloqueos caducados
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
