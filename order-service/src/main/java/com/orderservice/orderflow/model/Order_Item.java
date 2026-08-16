package com.orderservice.orderflow.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order_Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private BigInteger productId;

    @Column(name ="product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "total_price", precision = 19, scale = 2)    
    private BigDecimal totalPrice;
}
