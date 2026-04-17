package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.Inventory;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import java.util.List;

public interface InventoryService {

    Inventory saveInventory(Inventory inventory);
    List<Inventory> getAllInventory();
    Inventory getById(Integer id);
    List<Inventory> getByIsbn(String isbn);
    Inventory markAsPurchased(Integer id);

    ResponseStructure<String> deleteInventory(Integer id);
}