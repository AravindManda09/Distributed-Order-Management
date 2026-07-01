package com.oms.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.shared.kafka.KafkaTopics;
import com.oms.shared.messaging.events.OrderCancelledEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryConsumer {

    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = KafkaTopics.ORDER_EVENTS,
            groupId = "inventory-group"
    )
    public void consume(String message) {

        try {

            log.info("Received Order Cancelled Event: {}", message);

            OrderCancelledEvent event =
                    objectMapper.readValue(
                            message,
                            OrderCancelledEvent.class
                    );

            inventoryService.release(
                    event.getProductId(),
                    event.getQuantity()
            );

            log.info(
                    "Released inventory for Product {} Quantity {}",
                    event.getProductId(),
                    event.getQuantity()
            );

        } catch (Exception e) {

            log.error("Failed to process OrderCancelledEvent", e);

        }
    }
}