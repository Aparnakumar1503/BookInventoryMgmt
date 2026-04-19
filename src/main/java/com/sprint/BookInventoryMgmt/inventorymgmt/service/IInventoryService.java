package com.sprint.bookinventorymgmt.inventorymgmt.service;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import com.sprint.bookinventorymgmt.common.ResponseStructure;

import java.util.List;

public interface IInventoryService {

    Inventory saveInventory(Inventory inventory);

    List<Inventory> getAllInventory();

    Inventory getById(Integer id);

    List<Inventory> getByIsbn(String isbn);

    Inventory markAsPurchased(Integer id);

    ResponseStructure<String> deleteInventory(Integer id);

    ResponseStructure<Inventory> updateInventory(Integer id, Inventory updated);
}