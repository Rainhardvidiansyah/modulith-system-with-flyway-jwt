package com.rainhard.modulith.system.inventory.internal;
import com.rainhard.modulith.system.GenerateBatchNumber;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    private final JpaInventoryRepository inventoryRepository;
    private final JpaInventoryBatchRepository batchRepository;

    InventoryService(JpaInventoryRepository inventoryRepository,
                     JpaInventoryBatchRepository batchRepository) {
        this.inventoryRepository = inventoryRepository;
        this.batchRepository = batchRepository;
    }

    //First scenario
    //Get hit when ProductCreated event triggered — init first stock
    @Transactional
    public Inventory initStock(UUID productId, String warehouseCode, int quantityAvailable) {
        LOGGER.info("Init Stock inventory get Hit");

        var inventory = Inventory.create(productId, warehouseCode, quantityAvailable);
        var savedInventory = inventoryRepository.save(inventory);

        //Saved the first batch
        if(quantityAvailable > 0){
            var batch = InventoryBatch.create(savedInventory.getId(), GenerateBatchNumber.batchNumber(),
                    savedInventory.getQuantityAvailable(), BigDecimal.ZERO);
            batchRepository.save(batch);
        }
        return savedInventory;
    }


    //Second scenario
    //Restock: same inventory id with different quantity and date
    public Inventory restock(UUID inventoryId, int quantity, BigDecimal costPrice){
        var inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow();

        var newBatch = InventoryBatch.create(inventory.getId(), GenerateBatchNumber.batchNumber(), quantity, costPrice);
        batchRepository.save(newBatch);
        inventory.restockProduct(quantity);

        return inventoryRepository.save(inventory);
    }

    // Customer checkout → Lock the stock
    @Transactional
    public Inventory reserveStock(UUID inventoryId, int quantity) {
        var inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow();
        inventory.reserve(quantity);
        return inventoryRepository.save(inventory);
    }

    // Customer canceling the order / timeout → return the stock
    @Transactional
    public Inventory releaseStock(UUID inventoryId, int quantity) {
        var inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow();
        inventory.release(quantity);
        return inventoryRepository.save(inventory);
    }

    // Customer paid the bill → decrease stock permanently
    //Update deduct stock
    /*
    1. unfulfilled = 10  → "10 remain unfulfilled"
    2. unfulfilled = 0   → "all fulfilled, complete"
    3. unfulfilled > 0   → "insufficient stock to meet demand"
     */
    @Transactional
    public Inventory deductStock(UUID inventoryId, int quantity) {
        var inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow();

        var batches = batchRepository.findByInventoryIdAndQuantityGreaterThanOrderByReceivedAtAsc(inventoryId, quantity);
        int unfulfilled = quantity;

        for(InventoryBatch batch: batches){
            if(unfulfilled <= 0){
                break;
            }
            int deductFromBatch = Math.min(unfulfilled, batch.getQuantity());
            batch.deduct(deductFromBatch);
            batchRepository.save(batch);
            unfulfilled -= deductFromBatch;
        }

        if(unfulfilled > 0){
            throw new IllegalStateException("Insufficient stock across all batches");
        }

        inventory.deduct(quantity);
        return inventoryRepository.save(inventory);
    }


    //FACADE NEEDS ===
    /*
    findById(UUID id) ok
    findAll() ok
    findByProductId(UUID productId) ok
    restock(UUID inventoryId, RestockRequest request) ok
    findBatchesByInventoryId(UUID inventoryId)  ← optional ok
     */

    public Optional<Inventory> getInventoryById(UUID inventoryId){
        return inventoryRepository.findById(inventoryId);
    }

    public Optional<Inventory> getInventoryByProductId(UUID productId){
        return inventoryRepository.findInventoryByProductId(productId);
    }

    public List<Inventory> getAllInventories(){
        return inventoryRepository.findAll();
    }

    public List<InventoryBatch> getInventoryBatchByInventoryId(UUID inventoryId){
        return batchRepository.findInventoryBatchByInventoryId(inventoryId);
    }






}
