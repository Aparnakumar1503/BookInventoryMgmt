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

    // GET ALL
    @Override
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    // GET BY ID
    @Override
    public Inventory getInventoryById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id " + id));
    }

    // GET BY ISBN
    @Override
    public List<Inventory> getInventoryByISBN(String isbn) {
        return repository.findByIsbn(isbn);
    }

    // ADD INVENTORY
    @Override
    public Inventory addInventory(Inventory inventory) {
        inventory.setPurchased(false); // default value
        return repository.save(inventory);
    }

    // MARK AS PURCHASED
    @Override
    public Inventory markAsPurchased(Integer id) {
        Inventory inv = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id " + id));

        inv.setPurchased(true);
        return repository.save(inv);
    }
}
