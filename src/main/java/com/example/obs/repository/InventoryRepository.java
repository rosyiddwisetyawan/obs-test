package com.example.obs.repository;

import com.example.obs.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByItemId(Long itemId);
}