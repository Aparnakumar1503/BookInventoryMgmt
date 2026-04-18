package com.sprint.BookInventoryMgmt.InventoryMgmt.repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findByIsbn(String isbn);

    // Extra methods (keep but do not expose in controller)
    List<Inventory> findByPurchasedFalse();

    List<Inventory> findByRanksAndPurchased(int ranks, boolean purchased);

}