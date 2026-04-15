package com.sprint.BookInventoryMgmt.Inventory.Repository;

import com.sprint.BookInventoryMgmt.Inventory.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByIsbn(String isbn);
    List<Inventory> findByPurchasedFalse();
}