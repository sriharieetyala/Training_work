package org.chubb.webflux.webflux_flight.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("bookings")
public class Booking {
    @Id
    private Long id;

    private Long flightId;
    private String passengerName;
    private Integer seatsBooked;
    private String status;        // "BOOKED" or "CANCELLED"
    private LocalDateTime bookingTime;
}
