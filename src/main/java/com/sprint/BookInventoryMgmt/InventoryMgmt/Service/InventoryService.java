package com.sprint.BookInventoryMgmt.InventoryMgmt.Service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> getAllInventory();

    Inventory getInventoryById(Integer id);

    List<Inventory> getInventoryByISBN(String isbn);

    Inventory addInventory(Inventory inventory);

    Inventory markAsPurchased(Integer id);
}