package com.oms.payment;

import com.oms.shared.event.*;
import com.oms.shared.messaging.events.OrderCreatedEvent;
import com.oms.shared.messaging.events.PaymentCompletedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public void processPayment(OrderCreatedEvent event) {
        Random random = new Random();
        PaymentStatus status = random.nextInt(100) < 85
                ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        Payment payment = Payment.builder()
                .orderId(event.getOrderId())
                .amount(event.getTotalAmount())
                .status(status)
                .createdAt(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);

        PaymentCompletedEvent paymentCompletedEvent =
                PaymentCompletedEvent.builder()
                        .paymentId(payment.getId())
                        .orderId(event.getOrderId())
                        .amount(payment.getAmount())
                        .status(payment.getStatus())
                        .processedAt(LocalDateTime.now())
                        .build();

        eventPublisher.publish(
                EventType.PAYMENT_COMPLETED,
                payment.getId(),
                paymentCompletedEvent
        );
    }
}
