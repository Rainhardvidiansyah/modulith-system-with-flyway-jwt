package com.rainhard.modulith.system.inventory;

import com.rainhard.modulith.system.ResourceNotFoundException;
import com.rainhard.modulith.system.inventory.dto.*;
import com.rainhard.modulith.system.inventory.internal.InventoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
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
    public InventoryResponse getOneInventory(UUID inventoryId){
        return inventoryService.getInventoryById(inventoryId)
                .map(InventoryResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory id not found: " + inventoryId));
    }

    //Get All Inventory
    public List<InventoryResponse> getAllInventories(){
        try{
            return inventoryService.getAllInventories()
                    .stream()
                    .map(InventoryResponse::from)
                    .toList();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Inventories not listed");
        }
    }


    //Find Inventory by ProductId
    public InventoryResponse getInventoryByProductId(UUID productId){
        return inventoryService.getInventoryByProductId(productId)
                .map(InventoryResponse::from)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory with productId: " + productId + " not found") );
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
        try{
            var inventory = inventoryService.restock(inventoryId, quantity, costPrice);
            return InventoryResponse.from(inventory);
        }catch (NoSuchElementException e){
            throw new ResourceNotFoundException("Inventory id not found: " + inventoryId);
        }
    }

}
