package com.example.payment_service.service;

import com.gokul.order_service.events.PaymentCompletedEvent;
import com.gokul.order_service.events.PaymentFailedEvent;
import com.example.payment_service.model.Payment;
import com.example.payment_service.model.PaymentStatus;
import com.example.payment_service.repository.PaymentRepository;
import com.example.payment_service.kafka.PaymentEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    public void processPayment(String orderId) {

        // mock rule: always success for now
        boolean success = true;

        if (success) {
            Payment payment = new Payment(null, orderId, PaymentStatus.RESERVED);
            Payment saved = paymentRepository.save(payment);
            eventPublisher.publish(new PaymentCompletedEvent(orderId, saved.getPaymentId()));
        } else {
            eventPublisher.publish(new PaymentFailedEvent(orderId, "INSUFFICIENT_FUNDS"));
        }
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

}

