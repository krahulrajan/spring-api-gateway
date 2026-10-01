package com.example.orderservice;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping
    public String getOrders() {
        return "Orders from Order Service";
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable String id) {
        return "Order ID: " + id;
    }
}