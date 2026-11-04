package com.rainhard.modulith.system.order.dto;

import com.rainhard.modulith.system.order.internal.OrderResult;
import com.rainhard.modulith.system.order.internal.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID orderId,
                            UUID userId,
                            OrderStatus status,
                            BigDecimal totalAmount,
                            List<OrderItemResponse> items,
                            Instant createdAt) {

    public static OrderResponse from(OrderResult orderResult){
        return new OrderResponse(
                orderResult.order().getId(),
                orderResult.order().getUserId(),
                orderResult.order().getStatus(),
                orderResult.order().getTotalAmount(),
                orderResult.items().stream().map(
                        OrderItemResponse::from)
                        .toList(),
                orderResult.order().getCreatedAt()
        );
    }

}
