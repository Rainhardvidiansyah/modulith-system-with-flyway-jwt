package com.rainhard.modulith.system.inventory;

import com.rainhard.modulith.system.inventory.dto.*;
import com.rainhard.modulith.system.inventory.internal.InventoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryFacade {

    /*
    findById(UUID id) -> OK
    findAll() -> OK
    findByProductId(UUID productId) -> OK
    restock(UUID inventoryId, RestockRequest request)
    findBatchesByInventoryId(UUID inventoryId)  ← optional
     */


    private final InventoryService inventoryService;

    public InventoryFacade(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    //READ OPERATIONS ===

    //Get Inventory By Inventory id
    public Optional<InventoryResponse> getOneInventory(UUID inventoryId){
        return inventoryService.getInventoryById(inventoryId)
                .map(InventoryResponse::from);
    }

    //Get All Inventory
    public List<InventoryResponse> getAllInventories(){
       return inventoryService.getAllInventories()
               .stream()
               .map(InventoryResponse::from)
               .toList();
    }

    //Find Inventory by ProductId
    public Optional<InventoryResponse> getInventoryByProductId(UUID productId){
        return inventoryService.getInventoryByProductId(productId)
                .map(InventoryResponse::from);
    }


    //Find batch by inventory id
    public List<InventoryBatchResponse> findBatchesByInventoryId(UUID inventoryId){
        return inventoryService.getInventoryBatchByInventoryId(inventoryId)
                .stream()
                .map(InventoryBatchResponse::from)
                .toList();
    }


    // INSERT OPERATIONS ===

    //Restock
    public InventoryResponse restockInventory (UUID inventoryId, int quantity, BigDecimal costPrice){
        var inventory = inventoryService.restock(inventoryId, quantity, costPrice);
        return InventoryResponse.from(inventory);
    }

}
