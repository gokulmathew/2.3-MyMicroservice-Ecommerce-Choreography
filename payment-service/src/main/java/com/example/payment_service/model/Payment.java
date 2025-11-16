package com.example.payment_service.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payment {

    @Id
    private String paymentId;

    private String orderId;

    // Status can be: RESERVED | FAILED | REFUNDED
    private PaymentStatus status;
}
