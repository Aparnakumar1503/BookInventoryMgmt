package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory saveInventory(Inventory inventory);
    List<Inventory> getAllInventory();
    Inventory getById(Integer id);
    List<Inventory> getByIsbn(String isbn);
    Inventory markAsPurchased(Integer id);
}