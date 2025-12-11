package com.example.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/order/info")
    public String getOrderInfo() {
        return "Order Service is working fine!";
    }
}
