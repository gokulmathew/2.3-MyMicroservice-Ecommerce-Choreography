package com.gokul.order_service.service;

import com.gokul.order_service.model.Order;
import com.gokul.order_service.model.OrderStatus;
import com.gokul.order_service.events.OrderCreatedEvent;
import com.gokul.order_service.kafka.OrderEventPublisher;
import com.gokul.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(String userId, String productId, int quantity, BigDecimal amount) {

        Order order = Order.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .amount(amount)
                .status(OrderStatus.PENDING)
                .build();

        Order saved = orderRepository.save(order);

        // publish event
        OrderCreatedEvent event = new OrderCreatedEvent(
                saved.getId(), userId, productId, quantity, amount
        );
        eventPublisher.publishOrderCreated(event);

        return saved;
    }

    public Order getOrder(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void markCompleted(String orderId) {
        orderRepository.findById(orderId).ifPresent(o -> {
            o.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(o);
        });
    }

    public void markCancelled(String orderId) {
        orderRepository.findById(orderId).ifPresent(o -> {
            o.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(o);
        });
    }
}
