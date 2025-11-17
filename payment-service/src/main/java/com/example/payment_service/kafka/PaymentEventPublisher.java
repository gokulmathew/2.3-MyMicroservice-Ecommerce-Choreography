package com.example.payment_service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentEventsTopic;

    public PaymentEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kafka.topic.payment-events}") String paymentEventsTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentEventsTopic = paymentEventsTopic;
    }

    public void publish(Object event) {
        kafkaTemplate.send(paymentEventsTopic, event);
    }
}
