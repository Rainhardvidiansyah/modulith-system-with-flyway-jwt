package com.rainhard.modulith.system.order.dto;

import java.util.List;
import java.util.UUID;

public record OrderRequest(UUID userId, List<OrderItemRequest> items){}
