package com.oms.order.dto;

public record PlaceOrderRequest(
        Long productId,
        Integer quantity
) {
}