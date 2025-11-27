package org.chubb.webflux.webflux_flight.repo;

import org.chubb.webflux.webflux_flight.model.Flight;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface FlightRepository extends R2dbcRepository<Flight, Long> {

    Mono<Flight> findByFlightNumberAndDepartureTime(String flightNumber, LocalDateTime departureTime);

    Flux<Flight> findByOriginAndDestination(String origin, String destination);
}
