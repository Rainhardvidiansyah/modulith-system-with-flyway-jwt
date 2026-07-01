package com.rainhard.modulith.system.inventory.controller;


import com.rainhard.modulith.system.ApiResponse;
import com.rainhard.modulith.system.inventory.InventoryFacade;
import com.rainhard.modulith.system.inventory.dto.InventoryBatchResponse;
import com.rainhard.modulith.system.inventory.dto.InventoryResponse;
import com.rainhard.modulith.system.inventory.dto.InventoryRestockRequest;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryFacade inventoryFacade;


    public InventoryController(InventoryFacade inventoryFacade) {
        this.inventoryFacade = inventoryFacade;
    }


    /*
    findById(UUID id) -> OK ->
    findAll() -> OK
    findByProductId(UUID productId) -> OK
    restock(UUID inventoryId, RestockRequest request)
    findBatchesByInventoryId(UUID inventoryId)  ← optional
     */

    //FIND inventory by InventoryId
    @GetMapping("/get/{inventoryId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> findInventoryById(@PathVariable("inventoryId") UUID inventoryId){
        LOGGER.info("Method hit");

        LOGGER.info("Inventory id is: {}", inventoryId);

        return inventoryFacade.getOneInventory(inventoryId)
                        .map(response -> (ResponseEntity.ok(ApiResponse.success("Inventory successfully retrieved", response))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Inventory Not found", null)));
    }

    //FIND All Inventory
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> findAllInventory(){
        var inventories = inventoryFacade.getAllInventories();

        if(inventories.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.error("Inventories not Found", null));
        }

        return ResponseEntity.ok(ApiResponse.success("All Inventories successfully retrieved", inventories));
    }

    //FIND Inventory by ProductID
    @GetMapping("/product/{productId}")
    public ResponseEntity<@NonNull ApiResponse<InventoryResponse>> getInventoryByProductId(@PathVariable("productId") UUID productId){

        return inventoryFacade.getInventoryByProductId(productId)
                .map(response -> ResponseEntity.ok(ApiResponse.success("Inventory retrieved successfully", response)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Inventory not found", null)));
    }

    //FIND batch by Inventory id
    @GetMapping("/{inventoryId}")
    public ResponseEntity<ApiResponse<List<InventoryBatchResponse>>> findBatchByInventoryId(@PathVariable("inventoryId") UUID inventoryId){
        var inventoryBatches = inventoryFacade.findBatchesByInventoryId(inventoryId);

        if(inventoryBatches.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.error("Inventory batches not Found", null));
        }

        return ResponseEntity.ok(ApiResponse.success("All Inventory batches successfully retrieved", inventoryBatches));
    }


    //Restock
    //TODO: FINISH RESTOCK METHOD
    @PostMapping("/{uuid}/restock")
    public ResponseEntity<ApiResponse<InventoryResponse>> restockInventory(@PathVariable("uuid") UUID uuid,
                                                                           @RequestBody InventoryRestockRequest restockRequest){
        var restockData = inventoryFacade.restockInventory(uuid, restockRequest.getQuantity(), restockRequest.getCostPrice());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Restock is success", restockData));
    }




}

