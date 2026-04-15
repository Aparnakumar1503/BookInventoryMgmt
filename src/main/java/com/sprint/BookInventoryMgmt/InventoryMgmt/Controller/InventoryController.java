package com.sprint.BookInventoryMgmt.InventoryMgmt.Controller;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.Inventory;
import com.sprint.BookInventoryMgmt.InventoryMgmt.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository repository;

    // GET all inventory
    @GetMapping
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Inventory getById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow();
    }

    // GET by ISBN
    @GetMapping("/books/{isbn}")
    public List<Inventory> getByISBN(@PathVariable String isbn) {
        return repository.findByIsbn(isbn);
    }

    // POST create inventory
    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        inventory.setPurchased(false);
        return repository.save(inventory);
    }

    // PUT mark as purchased
    @PutMapping("/{id}/purchase")
    public Inventory markPurchased(@PathVariable Integer id) {
        Inventory inv = repository.findById(id).orElseThrow();
        inv.setPurchased(true);
        return repository.save(inv);
    }
}

