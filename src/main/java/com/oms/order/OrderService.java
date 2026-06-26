package com.oms.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.catalog.Product;
import com.oms.catalog.ProductService;
import com.oms.inventory.InventoryService;
import com.oms.order.dto.PlaceOrderRequest;
import com.oms.shared.event.OutboxEvent;
import com.oms.shared.event.OutboxEventRepository;
import com.oms.shared.event.OutboxEventStatus;
import com.oms.shared.messaging.events.OrderCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final ProductService productService;

    private final InventoryService inventoryService;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final OutboxEventRepository outboxEventRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public Order placeOrder(PlaceOrderRequest request) throws JsonProcessingException {
        Product product = productService.getProduct(request.productId());
        inventoryService.reserve(request.productId(), request.quantity());
        BigDecimal totalAmount = product.getPrice()
                .multiply(BigDecimal.valueOf(request.quantity()));
        Order order = Order.builder()
                .status(OrderStatus.PENDING_PAYMENT)
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .productId(product.getId())
                .quantity(request.quantity())
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING_PAYMENT)
                .build();

        String payload;
        try {
            payload = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize order event", e);
        }
        OutboxEvent outboxEvent = OutboxEvent.builder()
                .aggregateType("ORDER")
                .aggregateId(savedOrder.getId())
                .eventType("ORDER_CREATED")
                .payload(payload)
                .status(OutboxEventStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        outboxEventRepository.save(outboxEvent);

        OrderItem item = OrderItem.builder()
                .orderId(savedOrder.getId())
                .productId(product.getId())
                .quantity(request.quantity())
                .price(product.getPrice())
                .build();
        orderItemRepository.save(item);

        return savedOrder;
    }

}
