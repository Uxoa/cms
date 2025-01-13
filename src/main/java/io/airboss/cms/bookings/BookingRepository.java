package io.airboss.cms.bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<io.airboss.cms.bookings.Booking, Long> {
    List<io.airboss.cms.bookings.Booking> findByUserUserId(Long userId);
    List<io.airboss.cms.bookings.Booking> findByFlightFlightId(Long flightId);
}
