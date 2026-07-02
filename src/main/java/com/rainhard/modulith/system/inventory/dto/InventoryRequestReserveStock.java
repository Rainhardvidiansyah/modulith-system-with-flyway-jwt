package com.rainhard.modulith.system.inventory.dto;

import java.util.UUID;

public class InventoryRequestReserveStock {

    private UUID inventoryId;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }
}
