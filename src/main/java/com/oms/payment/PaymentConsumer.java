package com.oms.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.shared.kafka.KafkaTopics;
import com.oms.shared.messaging.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = KafkaTopics.ORDER_CREATED,
            groupId = "payment-group"
    )
    public void consume(String message) {
        try{
            log.info("Received order event: {}", message);
            OrderCreatedEvent event = objectMapper.readValue(message,
                    OrderCreatedEvent.class);
            paymentService.processPayment(event);
        }catch (Exception e){
            log.error("Failed to Process payment", e);
        }
    }
}
