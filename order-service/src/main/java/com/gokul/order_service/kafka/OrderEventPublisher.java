package com.gokul.order_service.kafka;

import com.gokul.order_service.events.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String orderEventsTopic;

    public OrderEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kafka.topic.order-events}") String orderEventsTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderEventsTopic = orderEventsTopic;
    }

    public void publishOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(orderEventsTopic, event);
    }
}
