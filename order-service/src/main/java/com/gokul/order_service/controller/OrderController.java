package com.gokul.order_service.controller;
import com.gokul.order_service.model.Order;
import com.gokul.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> request) {

        String userId = (String) request.get("userId");
        String productId = (String) request.get("productId");
        int quantity = (Integer) request.get("quantity");
        BigDecimal amount = new BigDecimal(request.get("amount").toString());

        Order order = orderService.createOrder(userId, productId, quantity, amount);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        Order order = orderService.getOrder(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }
}
