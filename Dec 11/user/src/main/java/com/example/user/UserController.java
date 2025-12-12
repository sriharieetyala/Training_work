package com.example.user;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String ORDER_URL = "http://localhost:8081/order/info";

    @GetMapping("/user/get-order")
    @CircuitBreaker(name = "orderCB", fallbackMethod = "orderFallback")
    @Retry(name = "orderCB")
    @RateLimiter(name = "orderCB")
    public String getOrder() {
        return restTemplate.getForObject(ORDER_URL, String.class);
    }

    public String orderFallback(Throwable t) {
        logger.warn("Fallback triggered! Reason: {}", t.getMessage());
        return "Order Service is DOWN — Fallback triggered!";
    }
}
