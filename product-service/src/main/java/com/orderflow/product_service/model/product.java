package com.orderflow.product_service.model;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private category category;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
