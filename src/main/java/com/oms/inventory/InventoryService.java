package com.oms.inventory;

import com.oms.shared.exception.InsufficientStockException;
import com.oms.shared.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public InventoryItem getInventory(Long productId){
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found"));
    }
    @Transactional
    public void reserve(Long productId,int qty){
        InventoryItem item = getInventory(productId);
        if(item.getQuantityAvailable() < qty) {
            throw new InsufficientStockException(
                    "Not enough stock");
        }
        item.setQuantityAvailable(item.getQuantityAvailable() - qty);
        item.setQuantityReserved(
                item.getQuantityReserved() + qty
        );
        inventoryRepository.save(item);
    }
    @Transactional
    public void release(Long productId,int qty){
        InventoryItem item = getInventory(productId);
        item.setQuantityAvailable(item.getQuantityAvailable() + qty);
        item.setQuantityReserved(item.getQuantityReserved() - qty);
        inventoryRepository.save(item);
    }

    public InventoryItem createInventory(
            InventoryItem item) {
        inventoryRepository.findByProductId(item.getProductId())
                .ifPresent(i -> {
                    throw new RuntimeException(
                            "Inventory already exists for product "
                                    + item.getProductId());
                });

        return inventoryRepository.save(item);
    }
}
