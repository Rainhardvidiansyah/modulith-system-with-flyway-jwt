package com.rainhard.modulith.system.inventory.dto;

import java.util.UUID;

public class InventoryRequestDeductStocks {

    private UUID inventoryId;

    private int quantity;

    public UUID getInventoryId() {
        return inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }
}
