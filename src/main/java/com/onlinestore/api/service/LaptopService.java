package com.onlinestore.api.service;

import com.onlinestore.api.dto.Inventory;
import com.onlinestore.api.dto.InventoryResponse;
import com.onlinestore.api.entity.Laptop;
import com.onlinestore.api.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaptopService {

    private final LaptopRepository laptopRepository;

    public Laptop saveLaptop(Laptop laptop){
        return laptopRepository.save(laptop);
    }

    public int getRemainingQty(String modelName){
        Optional<Laptop> laptop = laptopRepository.findByModelName(modelName);

        return laptop.map(Laptop::getAvailableQty).orElse(-1);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isAvailable(List<Inventory> inventories){

        return inventories.stream()
                .map(this::checkIfAvailable)
                .toList();
    }

    private InventoryResponse checkIfAvailable(Inventory inventory) {
        int qty = getRemainingQty(inventory.getModelName());
        return InventoryResponse.builder()
                .modelName(inventory.getModelName())
                .isAvailable(qty >= inventory.getQty())
                .build();
    }

    @Transactional()
    public void updateQuantities(List<Inventory> inventories){
        for (Inventory inventory : inventories) {
            updateQty(inventory);
        }
    }

    private void updateQty(Inventory inventory){
        Optional<Laptop> laptop = laptopRepository.findByModelName(inventory.getModelName());
        if(laptop.isPresent()){
            if(laptop.get().getAvailableQty() > inventory.getQty()){
                laptop.get().setAvailableQty(laptop.get().getAvailableQty() - inventory.getQty());
                saveLaptop(laptop.get());
            }
        }
    }

    public Optional<Laptop> findByModelName(String name) {
        return laptopRepository.findByModelName(name);
    }
}
