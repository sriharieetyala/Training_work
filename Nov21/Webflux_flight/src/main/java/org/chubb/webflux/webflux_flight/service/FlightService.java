package org.chubb.webflux.webflux_flight.service;

import lombok.RequiredArgsConstructor;
import org.chubb.webflux.webflux_flight.dto.FlightRequest;
import org.chubb.webflux.webflux_flight.exception.FlightAlreadyExistsException;
import org.chubb.webflux.webflux_flight.exception.ResourceNotFoundException;
import org.chubb.webflux.webflux_flight.model.Flight;
import org.chubb.webflux.webflux_flight.repo.FlightRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    // CREATE FLIGHT
    public Mono<Flight> createFlight(FlightRequest request) {
        return flightRepository
                .findByFlightNumberAndDepartureTime(
                        request.getFlightNumber(),
                        request.getDepartureTime()
                )
                // if flight exists -> emit error instead of Flight
                .flatMap(existing ->
                        Mono.<Flight>error(
                                new FlightAlreadyExistsException("Flight already exists for this time")
                        )
                )
                // if empty -> actually save the new flight
                .switchIfEmpty(
                        Mono.defer(() ->
                                flightRepository.save(
                                        Flight.builder()
                                                .flightNumber(request.getFlightNumber())
                                                .origin(request.getOrigin())
                                                .destination(request.getDestination())
                                                .departureTime(request.getDepartureTime())
                                                .totalSeats(request.getTotalSeats())
                                                .availableSeats(request.getTotalSeats())
                                                .price(request.getPrice())
                                                .build()
                                )
                        )
                );
    }

    // FIND BY ID (404 if not found)
    public Mono<Flight> getFlightById(Long id) {
        return flightRepository.findById(id)
                .switchIfEmpty(
                        Mono.<Flight>error(
                                new ResourceNotFoundException("Flight not found")
                        )
                );
    }

    // SEARCH
    public Flux<Flight> searchFlights(String origin, String destination) {
        return flightRepository.findByOriginAndDestination(origin, destination);
    }
}