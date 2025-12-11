package com.example.user;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String ORDER_URL = "http://localhost:8082/order/info";

    @GetMapping("/user/get-order")
    @CircuitBreaker(name = "orderCB", fallbackMethod = "orderFallback")
    public String getOrder() {
        return restTemplate.getForObject(ORDER_URL, String.class);
    }

    // IMPORTANT: fallback must accept Throwable
    public String orderFallback(Throwable t) {
        return "Order Service is DOWN — Fallback triggered!";
    }
}
