package org.chubb.webflux.webflux_flight.service;

import lombok.RequiredArgsConstructor;
import org.chubb.webflux.webflux_flight.dto.BookingRequest;
import org.chubb.webflux.webflux_flight.exception.NotEnoughSeatsException;
import org.chubb.webflux.webflux_flight.exception.ResourceNotFoundException;
import org.chubb.webflux.webflux_flight.model.Booking;
import org.chubb.webflux.webflux_flight.model.Flight;
import org.chubb.webflux.webflux_flight.repo.BookingRepository;
import org.chubb.webflux.webflux_flight.repo.FlightRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    // Book tickets: reduces availableSeats, creates booking
    public Mono<Booking> bookTickets(BookingRequest request) {
        return flightRepository.findById(request.getFlightId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Flight not found")))
                .flatMap(flight -> {
                    if (flight.getAvailableSeats() < request.getSeatsBooked()) {
                        return Mono.error(new NotEnoughSeatsException("Not enough seats available"));
                    }

                    flight.setAvailableSeats(flight.getAvailableSeats() - request.getSeatsBooked());

                    Mono<Flight> updateFlight = flightRepository.save(flight);

                    Mono<Booking> saveBooking = bookingRepository.save(
                            Booking.builder()
                                    .flightId(flight.getId())
                                    .passengerName(request.getPassengerName())
                                    .seatsBooked(request.getSeatsBooked())
                                    .status("BOOKED")
                                    .bookingTime(LocalDateTime.now())
                                    .build()
                    );

                    return updateFlight.then(saveBooking);
                });
    }

    // Cancel booking: mark CANCELLED and restore seats
    public Mono<Booking> cancelBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Booking not found")))
                .flatMap(booking -> {
                    if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
                        return Mono.just(booking); // already cancelled
                    }

                    booking.setStatus("CANCELLED");

                    Mono<Flight> updateFlightSeats = flightRepository.findById(booking.getFlightId())
                            .flatMap(flight -> {
                                flight.setAvailableSeats(flight.getAvailableSeats() + booking.getSeatsBooked());
                                return flightRepository.save(flight);
                            });

                    return updateFlightSeats.then(bookingRepository.save(booking));
                });
    }
}
