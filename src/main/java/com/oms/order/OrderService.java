package com.oms.order;

import com.oms.catalog.Product;
import com.oms.catalog.ProductService;
import com.oms.inventory.InventoryService;
import com.oms.order.dto.PlaceOrderRequest;
import com.oms.shared.event.EventPublisher;
import com.oms.shared.event.EventType;
import com.oms.shared.exception.ResourceNotFoundException;
import com.oms.shared.messaging.events.OrderCancelledEvent;
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

    private final EventPublisher eventPublisher;

    @Transactional
    public Order placeOrder(PlaceOrderRequest request) {

        // Validate product
        Product product = productService.getProduct(request.productId());

        // Reserve inventory
        inventoryService.reserve(request.productId(), request.quantity());

        // Calculate order amount
        BigDecimal totalAmount = product.getPrice()
                .multiply(BigDecimal.valueOf(request.quantity()));

        // Create order
        Order order = Order.builder()
                .status(OrderStatus.PENDING_PAYMENT)
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        // Create order item
        OrderItem orderItem = OrderItem.builder()
                .orderId(savedOrder.getId())
                .productId(product.getId())
                .quantity(request.quantity())
                .price(product.getPrice())
                .build();

        orderItemRepository.save(orderItem);

        // Publish business event
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .productId(product.getId())
                .quantity(request.quantity())
                .totalAmount(totalAmount)
                .orderStatus(savedOrder.getStatus())
                .createdAt(LocalDateTime.now())
                .build();

        eventPublisher.publish(
                EventType.ORDER_CREATED,
                savedOrder.getId(),
                event
        );

        return savedOrder;
    }

    @Transactional
    public void confirmOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            return;
        }

        order.setStatus(OrderStatus.CONFIRMED);
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            return;
        }

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Cannot cancel a confirmed order.");
        }

        order.setStatus(OrderStatus.CANCELLED);

        OrderItem orderItem = orderItemRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order item not found for order: " + orderId));

        OrderCancelledEvent event = OrderCancelledEvent.builder()
                .orderId(orderId)
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .cancelledAt(LocalDateTime.now())
                .build();

        eventPublisher.publish(
                EventType.ORDER_CANCELLED,
                orderId,
                event
        );
    }
}