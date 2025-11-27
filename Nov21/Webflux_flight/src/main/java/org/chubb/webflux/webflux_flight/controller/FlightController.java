package org.chubb.webflux.webflux_flight.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chubb.webflux.webflux_flight.dto.FlightRequest;
import org.chubb.webflux.webflux_flight.dto.IdResponse;
import org.chubb.webflux.webflux_flight.model.Flight;
import org.chubb.webflux.webflux_flight.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;


@RestController
    @RequestMapping("/api/flights")
    @RequiredArgsConstructor
    public class FlightController {

        private final FlightService flightService;

        // ADD FLIGHT: 201 + only id in body
        @PostMapping
        public Mono<ResponseEntity<IdResponse>> createFlight(@Valid @RequestBody FlightRequest request) {
            return flightService.createFlight(request)
                    .map(saved -> ResponseEntity
                            .created(URI.create("/api/flights/" + saved.getId()))
                            .body(new IdResponse(saved.getId())));
        }

        // GET by id: 404 if not found (handled in service)
        @GetMapping("/{id}")
        public Mono<Flight> getFlightById(@PathVariable Long id) {
            return flightService.getFlightById(id);
        }

        // SEARCH by origin + destination
        @GetMapping("/search")
        public Flux<Flight> searchFlights(@RequestParam String origin,
                                          @RequestParam String destination) {
            return flightService.searchFlights(origin, destination);
        }
    }

