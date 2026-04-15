package com.sprint.BookInventoryMgmt.InventoryMgmt.Repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByIsbn(String isbn);
    List<Inventory> findByPurchasedFalse();
}