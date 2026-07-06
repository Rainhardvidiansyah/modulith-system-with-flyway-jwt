package com.rainhard.modulith.system.order.dto;

import com.rainhard.modulith.system.order.internal.OrderItem;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(UUID productId, int quantity, BigDecimal unitPrice) {


    public static OrderItemResponse from(OrderItem orderItem){
        return new OrderItemResponse(orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice());
    }

}
