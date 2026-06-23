package com.oms.order;

import com.oms.catalog.Product;
import com.oms.catalog.ProductService;
import com.oms.inventory.InventoryService;
import com.oms.order.dto.PlaceOrderRequest;
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

    @Transactional
    public Order placeOrder(PlaceOrderRequest request) {
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
