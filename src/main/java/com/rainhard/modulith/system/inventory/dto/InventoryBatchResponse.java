package com.rainhard.modulith.system.inventory.dto;

import com.rainhard.modulith.system.inventory.internal.InventoryBatch;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record InventoryBatchResponse(
        UUID inventoryId,
        String batchNumber,
        int quantity,
        Instant receivedAt,
        BigDecimal costPrice
) {

    public static InventoryBatchResponse from(InventoryBatch batch) {
        return new InventoryBatchResponse(
                batch.getInventoryId(),
                batch.getBatchNumber(),
                batch.getQuantity(),
                batch.getReceivedAt(),
                batch.getCostPrice()
        );
    }
}
