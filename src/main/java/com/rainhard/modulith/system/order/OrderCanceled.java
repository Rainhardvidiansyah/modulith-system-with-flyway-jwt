package com.rainhard.modulith.system.order;

import java.util.List;
import java.util.UUID;

public record OrderCanceled (UUID orderId, List<CanceledItem> canceledItem){

    public record CanceledItem(UUID productId, int quantity){}
}
