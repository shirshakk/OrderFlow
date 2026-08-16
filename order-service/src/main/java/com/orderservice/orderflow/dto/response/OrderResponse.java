package com.orderservice.orderflow.dto.response;

import com.orderservice.orderflow.model.Order;
import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(
        Long id,
        String orderNumber,
        Long userId,
        Order.StatusOptions orderStatus,
        BigDecimal orderAmount,
        String currency,
        Instant createdAt,
        Instant updatedAt
) {
    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getUserId(),
                order.getOrderStatus(),
                order.getOrderAmount(),
                order.getCurrency(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}