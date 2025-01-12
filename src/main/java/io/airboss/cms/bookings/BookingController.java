package io.airboss.cms.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @GetMapping("/me")
    public ResponseEntity<List<Booking>> getMyBookings(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(bookingRepository.findByUserUserId(userId));
    }
    
    @PostMapping
    public ResponseEntity<Booking> createBooking(
          @RequestParam Long flightId,
          @RequestParam int numberOfSeats,
          Authentication authentication) {
        
        Long userId = Long.valueOf(authentication.getName());
        Booking booking = bookingService.createBooking(userId, flightId, numberOfSeats);
        return ResponseEntity.ok(booking);
    }
    
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingRepository.findAll());
    }
    
    @PostMapping("/confirm/{bookingId}")
    public ResponseEntity<String> confirmBooking(@PathVariable Long bookingId) {
        bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok("Reserva confirmada exitosamente");
    }
    
    @PostMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Reserva cancelada exitosamente");
    }
    
}
