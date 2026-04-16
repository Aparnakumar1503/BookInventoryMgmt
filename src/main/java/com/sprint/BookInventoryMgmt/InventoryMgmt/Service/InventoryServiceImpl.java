package com.sprint.BookInventoryMgmt.InventoryMgmt.Service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.Inventory;
import com.sprint.BookInventoryMgmt.InventoryMgmt.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Override
    public Inventory saveInventory(Inventory inventory) {
        return repository.save(inventory);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    @Override
    public List<Inventory> getByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

    @Override
    public List<Inventory> getAvailableBooks() {
        return repository.findByPurchasedFalse();
    }

    @Override
    public List<Inventory> getByRankAndStatus(int rank, boolean status) {
        return repository.findByRanksAndPurchased(rank, status);
    }
}