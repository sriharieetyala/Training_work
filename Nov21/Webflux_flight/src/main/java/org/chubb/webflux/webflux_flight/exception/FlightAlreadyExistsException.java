package org.chubb.webflux.webflux_flight.exception;

public class FlightAlreadyExistsException extends RuntimeException {

    public FlightAlreadyExistsException(String message) {
        super(message);
    }
}
