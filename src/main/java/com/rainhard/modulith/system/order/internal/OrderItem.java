package com.rainhard.modulith.system.order.internal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
public class OrderItem {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(updatable = false, nullable = false, name = "order_id")
    private UUID orderId;

    @Column(updatable = false, nullable = false, name = "product_id")
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 12, scale = 2, updatable = false)
    private BigDecimal price;


    //Factory method:

    public static OrderItem create(UUID orderId, UUID productId, int quantity, BigDecimal price){
        var orderItem = new OrderItem();
        orderItem.orderId = orderId;
        orderItem.productId = productId;
        orderItem.quantity = quantity;
        orderItem.price = price;
        return orderItem;
    }
}


/*
CREATE TABLE order_items (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(12,2) NOT NULL
);
 */