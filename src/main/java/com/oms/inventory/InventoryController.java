package com.oms.inventory;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping("/{productId}")
    public InventoryItem getInventory(@PathVariable Long productId) {
        return inventoryService.getInventory(productId);
    }
    @PostMapping("/{productId}/reserve")
    public String reserve(@PathVariable Long productId,
                          @RequestParam int qty) {
        inventoryService.reserve(productId, qty);
        return "Stock Reserved";
    }
    @PostMapping("/{productId}/release")
    public String release(@PathVariable Long productId,
                            @RequestParam int qty) {
        inventoryService.release(productId, qty);
        return "Stock Released";
    }
    @PostMapping
    public InventoryItem createInventory(
            @RequestBody InventoryItem item) {

        return inventoryService.createInventory(item);
    }

}
