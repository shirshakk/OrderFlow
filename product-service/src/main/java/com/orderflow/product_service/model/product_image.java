package com.orderflow.product_service.model;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class product_image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private product product;

    @Column(name = "s3_key")
    private String s3Key;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;
}
