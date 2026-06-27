package com.rainhard.modulith.system.inventory.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class InventoryInitStockRequest {

    @NotNull(message = "Product id cannot be null")
    private UUID productId;

    @NotNull(message = "Ware house code cannot be null")
    private String warehouseCode;

    @NotNull(message = "Quantity Or Available Stock cannot be null")
    private int quantity;

    public UUID getProductId() {
        return productId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public int getQuantity() {
        return quantity;
    }
}
