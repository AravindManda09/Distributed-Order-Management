package com.oms.shared.messaging.events;

import com.oms.order.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;
}