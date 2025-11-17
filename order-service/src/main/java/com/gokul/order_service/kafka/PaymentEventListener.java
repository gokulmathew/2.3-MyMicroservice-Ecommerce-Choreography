package com.gokul.order_service.kafka;

import com.gokul.order_service.events.PaymentCompletedEvent;
import com.gokul.order_service.model.Order;
import com.gokul.order_service.model.OrderStatus;
import com.gokul.order_service.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    private final OrderRepository orderRepository;

    public PaymentEventListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "payment-events", groupId = "order-service-group")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        System.out.println("Order-Service received PaymentCompletedEvent: " + event);

        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            System.out.println("Order updated to COMPLETED in DB");
        });
    }
}
