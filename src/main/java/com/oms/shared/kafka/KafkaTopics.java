package com.oms.shared.kafka;

public final class KafkaTopics {

    private KafkaTopics() {}

    public static final String ORDER_CREATED = "order-created";

    public static final String ORDER_EVENTS = "order-events";

    public static final String PAYMENT_EVENTS = "payment-events";
}