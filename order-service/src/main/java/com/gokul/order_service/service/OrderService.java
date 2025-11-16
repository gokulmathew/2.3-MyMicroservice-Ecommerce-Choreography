package com.gokul.order_service.service;

import com.gokul.order_service.model.Order;
import com.gokul.order_service.model.OrderStatus;
import com.gokul.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(String userId, String productId, int quantity, BigDecimal amount) {

        Order order = Order.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .amount(amount)
                .status(OrderStatus.PENDING)
                .build();

        return orderRepository.save(order);
    }

    public Order getOrder(String id) {
        return orderRepository.findById(id).orElse(null);
    }
}
