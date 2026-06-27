package com.rainhard.modulith.system.inventory.dto;
import com.rainhard.modulith.system.inventory.internal.Inventory;
import java.util.Objects;
import java.util.UUID;


public record InventoryResponse (UUID inventoryId, UUID productId,
                                 String warehouseCode, int quantityAvailable){


    public static InventoryResponse from(Inventory inventory){
        Objects.requireNonNull(inventory, "Inventory cannot be null");

        return new InventoryResponse(inventory.getId(), inventory.getProductId(),
                inventory.getWarehouseCode(), inventory.getQuantityAvailable());
    }
}





