package com.sprint.BookInventoryMgmt.inventorymgmt.repository;

import com.sprint.BookInventoryMgmt.inventorymgmt.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findByIsbn(String isbn);

    // Extra methods (keep but do not expose in controller)
    List<Inventory> findByPurchasedFalse();

    List<Inventory> findByRanksAndPurchased(int ranks, boolean purchased);

}