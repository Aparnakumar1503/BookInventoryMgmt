package com.sprint.BookInventoryMgmt.InventoryMgmt.Service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.Inventory;
import java.util.List;

public interface InventoryService {

    Inventory saveInventory(Inventory inventory);

    List<Inventory> getAllInventory();

    List<Inventory> getByIsbn(String isbn);

    List<Inventory> getAvailableBooks();

    List<Inventory> getByRankAndStatus(int rank, boolean status);
}