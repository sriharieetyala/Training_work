package org.chubb.webflux.webflux_flight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookingRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotBlank(message = "Passenger name must not be blank")
    private String passengerName;

    @NotNull(message = "Seats booked is required")
    @Positive(message = "Seats booked must be positive")
    private Integer seatsBooked;
}
