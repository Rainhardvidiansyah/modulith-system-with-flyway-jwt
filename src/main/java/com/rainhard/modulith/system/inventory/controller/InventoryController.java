package com.rainhard.modulith.system.inventory.controller;



import com.rainhard.modulith.system.inventory.InventoryFacade;
import com.rainhard.modulith.system.inventory.dto.InventoryBatchResponse;
import com.rainhard.modulith.system.inventory.dto.InventoryResponse;
import com.rainhard.modulith.system.inventory.dto.InventoryRestockRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public InventoryResponse findInventoryById(@PathVariable("inventoryId") UUID inventoryId){


        LOGGER.info("Inventory id in findInventoryById method is: {}", inventoryId);

        return inventoryFacade.getOneInventory(inventoryId);
    }


//    //FIND All Inventory
    @GetMapping("/")
    public List<InventoryResponse> findAllInventory(){
        return inventoryFacade.getAllInventories();
    }


//    //FIND Inventory by ProductID
    @GetMapping("/product/{productId}")
    public InventoryResponse getInventoryByProductId(@PathVariable("productId") UUID productId){
        return inventoryFacade.getInventoryByProductId(productId);
    }


//    //FIND batch by Inventory id
    @GetMapping("/batch/{inventoryId}")
    public List<InventoryBatchResponse> findBatchByInventoryId(@PathVariable("inventoryId") UUID inventoryId){
        LOGGER.info("Method find batch by inventory id get hit...");
        return inventoryFacade.findBatchesByInventoryId(inventoryId);
    }


//    //RESTOCK Data
    @PostMapping("/{inventoryId}/restock")
    public InventoryResponse restockInventory(@PathVariable("inventoryId") UUID inventoryId,
                                                                           @RequestBody InventoryRestockRequest restockRequest){
        LOGGER.info("Restock method is hit");
        var restockData = inventoryFacade.restockInventory(inventoryId, restockRequest.getQuantity(), restockRequest.getCostPrice());
        LOGGER.info("Data for restock: {}", restockData);
        return restockData;
    }




}

