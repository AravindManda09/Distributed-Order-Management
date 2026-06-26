package com.oms.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oms.order.dto.PlaceOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

        private final OrderService orderService;

        @PostMapping
        public Order placeOrder(@RequestBody PlaceOrderRequest request) throws JsonProcessingException {
            return orderService.placeOrder(request);
        }
}
