package com.rainhard.modulith.system.inventory;

import com.rainhard.modulith.system.inventory.dto.*;
import com.rainhard.modulith.system.inventory.internal.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryFacade {

    private final InventoryService inventoryService;

    public InventoryFacade(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    public InventoryResponse createInventory(InventoryInitStockRequest request){

        var inventory = inventoryService.initStock(request.getProductId(),
                request.getWarehouseCode(), request.getQuantity());

        return InventoryResponse.from(inventory);
    }

    public InventoryResponse cancelStock(InventoryRequestReverseStock inventoryRequest){
        var inventory = inventoryService.reserveStock(inventoryRequest.getInventoryId(),
                inventoryRequest.getQuantity());
        return InventoryResponse.from(inventory);
    }

    public InventoryResponse releaseStock(InventoryRequestReleaseStock releaseStock){
        var inventory = inventoryService.releaseStock(releaseStock.getInventoryId(),
                releaseStock.getQuantity());
        return InventoryResponse.from(inventory);
    }

    public InventoryResponse deductStocks(InventoryRequestDeductStocks deductStocks){
        var inventory = inventoryService.deductStock(deductStocks.getInventoryId(), deductStocks.getQuantity());
        return InventoryResponse.from(inventory);
    }

    public List<InventoryResponse> getAllInventories(){
       return inventoryService.getAllInventories()
               .stream()
               .map(InventoryResponse::from)
               .toList();
    }
}
