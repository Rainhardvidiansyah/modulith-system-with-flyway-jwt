package com.rainhard.modulith.system.order.dto;



import java.util.UUID;


public record OrderItemRequest(UUID productId, int quantity) {}