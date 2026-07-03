package com.rainhard.modulith.system.order.internal;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    //Factory method:
    public static Order create(UUID userId, OrderStatus orderStatus, BigDecimal totalAmount){
        var order = new Order();
        order.userId = userId;
        order.status = OrderStatus.PENDING;
        order.totalAmount = totalAmount;
        order.createdAt = Instant.now();
        order.updatedAt = Instant.now();
        return order;
    }

    //Three business methods:
    public void confirmOrder(){
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = Instant.now();
    }


    public void cancelOrder(){
        this.status = OrderStatus.CANCELED;
        this.updatedAt = Instant.now();
    }

    public void completedOrder(){
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}


/*
CREATE TABLE orders (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    total_amount NUMERIC(12,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);
 */