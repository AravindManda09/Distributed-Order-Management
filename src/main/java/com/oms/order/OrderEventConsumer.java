package com.oms.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.payment.PaymentStatus;
import com.oms.shared.kafka.KafkaTopics;
import com.oms.shared.messaging.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_EVENTS,
            groupId = "order-group"
    )
    public void consume(String message) {

        try {

            log.info("Received Payment Event: {}", message);

            PaymentCompletedEvent event =
                    objectMapper.readValue(
                            message,
                            PaymentCompletedEvent.class
                    );

            if (event.getStatus() == PaymentStatus.SUCCESS) {

                orderService.confirmOrder(event.getOrderId());

            } else {

                orderService.cancelOrder(event.getOrderId());

            }

        } catch (Exception e) {

            log.error("Failed to process payment event", e);

        }
    }
}