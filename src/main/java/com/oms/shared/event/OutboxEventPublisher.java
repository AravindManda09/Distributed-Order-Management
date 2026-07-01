package com.oms.shared.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher implements EventPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(EventType eventType,
                        Long aggregateId,
                        Object event) {

        try {

            String payload = objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .aggregateType(resolveAggregateType(eventType))
                    .aggregateId(aggregateId)
                    .eventType(eventType)
                    .payload(payload)
                    .status(OutboxEventStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            outboxEventRepository.save(outboxEvent);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Failed to serialize event: " + eventType,
                    e
            );

        }

    }

   private String resolveAggregateType(EventType eventType) {
        return switch (eventType){
            case ORDER_CREATED,
                 ORDER_CANCELLED -> "ORDER";
            case PAYMENT_COMPLETED -> "PAYMENT_COMPLETED";
        };
   }

}