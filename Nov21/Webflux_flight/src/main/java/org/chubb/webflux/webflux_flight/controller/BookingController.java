package org.chubb.webflux.webflux_flight.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chubb.webflux.webflux_flight.dto.BookingRequest;
import org.chubb.webflux.webflux_flight.dto.IdResponse;
import org.chubb.webflux.webflux_flight.model.Booking;
import org.chubb.webflux.webflux_flight.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // BOOK tickets: 201 + only id in response
    @PostMapping
    public Mono<ResponseEntity<IdResponse>> bookTickets(@Valid @RequestBody BookingRequest request) {
        return bookingService.bookTickets(request)
                .map(saved -> ResponseEntity
                        .created(URI.create("/api/bookings/" + saved.getId()))
                        .body(new IdResponse(saved.getId())));
    }

    // CANCEL booking
    @PostMapping("/{id}/cancel")
    public Mono<Booking> cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
}