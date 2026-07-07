package com.rainhard.modulith.system.order;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderPlaced
        (UUID orderId, UUID userId, BigDecimal totalAmount, List<OrderedItem> items){

    public record OrderedItem(
            UUID productId,
            int quantity
    ){}
}

