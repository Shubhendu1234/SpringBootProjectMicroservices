package com.techie.microservices.inventory.service;

import com.techie.microservices.inventory.model.Inventory;
import com.techie.microservices.inventory.reository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode,Integer quantity) {
        //find inventory for a given skucode for the quantity greater than or equal to 0
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode,quantity);
    }


}
