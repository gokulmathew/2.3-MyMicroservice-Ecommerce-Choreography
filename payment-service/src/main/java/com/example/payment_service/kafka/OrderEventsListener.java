package com.example.payment_service.kafka;


import com.gokul.order_service.events.OrderCreatedEvent;
import com.example.payment_service.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsListener {

    private final PaymentService paymentService;

    public OrderEventsListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "${kafka.topic.order-events}", groupId = "payment-service-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Payment-service received OrderCreatedEvent: " + event);
        paymentService.processPayment(event.getOrderId());
    }
}
