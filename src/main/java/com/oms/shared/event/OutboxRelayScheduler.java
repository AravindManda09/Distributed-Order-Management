package com.oms.shared.event;

import com.oms.shared.kafka.KafkaProducer;
import com.oms.shared.kafka.KafkaTopics;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRelayScheduler {
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaProducer kafkaProducer;
    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void relayEvents() {

        log.info("Scheduler started");

        List<OutboxEvent> events =
                outboxEventRepository.findByStatus(OutboxEventStatus.PENDING);

        log.info("Found {} pending events", events.size());

        for (OutboxEvent event : events) {

            try {

                String topic;

                switch (event.getEventType()) {

                    case ORDER_CREATED ->
                            topic = KafkaTopics.ORDER_CREATED;

                    case PAYMENT_COMPLETED ->
                            topic = KafkaTopics.PAYMENT_EVENTS;

                    case ORDER_CANCELLED ->
                            topic = KafkaTopics.ORDER_EVENTS;

                    default ->
                            throw new IllegalStateException(
                                    "Unexpected event type: " + event.getEventType());
                }
                kafkaProducer.publish(topic, event.getPayload());
                event.setStatus(OutboxEventStatus.SENT);

                outboxEventRepository.save(event);

            } catch (Exception ex) {

                log.error("Failed to publish event {}", event.getId(), ex);
            }
        }
    }
}

