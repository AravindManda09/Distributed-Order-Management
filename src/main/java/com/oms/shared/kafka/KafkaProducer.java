package com.oms.shared.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String topic, String payload) {

        try {

            kafkaTemplate.send(topic, payload).get();

            log.info("Published event to Kafka topic {}", topic);

        } catch (Exception e) {

            log.error("Failed to publish event", e);

            throw new RuntimeException(e);
        }
    }
}