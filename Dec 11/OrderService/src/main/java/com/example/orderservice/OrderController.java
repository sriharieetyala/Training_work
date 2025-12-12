package com.example.orderservice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private int counter = 0;

    @GetMapping("/order/info")
    public String getOrderInfo() {
        counter++;
        if (counter <= 2) { // first 2 calls fail
            throw new RuntimeException("Simulated failure");
        }
        return "Order info retrieved successfully!";
    }
}
