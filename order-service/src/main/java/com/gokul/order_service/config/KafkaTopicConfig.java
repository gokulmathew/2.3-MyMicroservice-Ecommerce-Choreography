package com.gokul.order_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.order-events}")
    private String orderEventsTopic;

    @Value("${kafka.topic.payment-events}")
    private String paymentEventsTopic;

    @Bean
    public NewTopic orderEvents() {
        return new NewTopic(orderEventsTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic paymentEvents() {
        return new NewTopic(paymentEventsTopic, 1, (short) 1);
    }
}
