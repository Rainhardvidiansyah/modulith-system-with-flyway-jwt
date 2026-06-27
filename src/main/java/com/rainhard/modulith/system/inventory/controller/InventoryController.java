package com.rainhard.modulith.system.inventory.controller;


import com.rainhard.modulith.system.inventory.InventoryFacade;
import com.rainhard.modulith.system.inventory.internal.InventoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryFacade inventoryFacade;


    public InventoryController(InventoryFacade inventoryFacade) {
        this.inventoryFacade = inventoryFacade;
    }
}
