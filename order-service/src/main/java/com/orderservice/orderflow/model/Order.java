package com.orderservice.orderflow.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private StatusOptions orderStatus;

    @Column(name = "order_amount", precision = 19, scale = 2)
    private BigDecimal orderAmount;

    private String currency;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

   
    public enum StatusOptions {
        CREATED,
        PAYMENT_PENDING,
        PAYMENT_FAILED,
        PAYMENT_SUCCESSFUL,
        INVENTORY_PENDING,
        INVENTORY_FAILED,
        INVENTORY_RESERVED,
        CONFIRMED,
        REFUNDED,
        REFUND_PENDING,
        CANCELLED
    }
}