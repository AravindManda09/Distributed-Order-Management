package com.oms.shared.messaging.events;


import com.oms.order.OrderStatus;
import com.oms.payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent {
    private Long paymentId;

    private Long orderId;

    private BigDecimal amount;

    private PaymentStatus status;

    private LocalDateTime processedAt;
}
