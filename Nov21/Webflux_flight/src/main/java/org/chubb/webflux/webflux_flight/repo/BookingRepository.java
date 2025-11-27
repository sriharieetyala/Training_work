package org.chubb.webflux.webflux_flight.repo;

import org.chubb.webflux.webflux_flight.model.Booking;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface BookingRepository extends R2dbcRepository<Booking, Long> {

    Flux<Booking> findByFlightId(Long flightId);
}
