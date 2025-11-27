package org.chubb.webflux.webflux_flight.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleNotFound(ResourceNotFoundException ex) {
        return Mono.just(buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(FlightAlreadyExistsException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleFlightExists(FlightAlreadyExistsException ex) {
        return Mono.just(buildResponse(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(NotEnoughSeatsException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleSeats(NotEnoughSeatsException ex) {
        return Mono.just(buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Validation errors from @Valid on WebFlux controllers


    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
