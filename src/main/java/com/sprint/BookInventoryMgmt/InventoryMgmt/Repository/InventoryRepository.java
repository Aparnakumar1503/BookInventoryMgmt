package com.sprint.BookInventoryMgmt.InventoryMgmt.Repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    // existing methods
    List<Inventory> findByIsbn(String isbn);

    List<Inventory> findByPurchasedFalse();

    List<Inventory> findByRanksAndPurchased(int ranks, boolean purchased);

}