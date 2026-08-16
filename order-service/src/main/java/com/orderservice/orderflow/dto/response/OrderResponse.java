package com.orderservice.orderflow.dto.response;

import com.orderservice.orderflow.model.Order;
import java.math.BigDecimal;
import java.time.Instant;

public class OrderResponse {
    private final Long id;
    private final String orderNumber;
    private final Long userId;
    private final Order.StatusOptions orderStatus;
    private final BigDecimal orderAmount;
    private final String currency;
    private final Instant createdAt;
    private final Instant updatedAt;

    public OrderResponse(Long id, String orderNumber, Long userId, Order.StatusOptions orderStatus,
                         BigDecimal orderAmount, String currency, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getOrderNumber() { return orderNumber; }
    public Long getUserId() { return userId; }
    public Order.StatusOptions getOrderStatus() { return orderStatus; }
    public BigDecimal getOrderAmount() { return orderAmount; }
    public String getCurrency() { return currency; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

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