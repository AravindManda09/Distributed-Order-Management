package com.oms.shared.event;

public interface EventPublisher {
    void publish(
            EventType eventType,
            Long aggregateId,
            Object event
    );
}
