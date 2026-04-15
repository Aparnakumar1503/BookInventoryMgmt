package com.sprint.BookInventoryMgmt.Inventory.Controller;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.Inventory;
import com.sprint.BookInventoryMgmt.InventoryMgmt.Service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    // ✅ GET all inventory
    @GetMapping
    public List<Inventory> getAllInventory() {
        return service.getAllInventory();
    }

    // ✅ GET inventory by ID
    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable Integer id) {
        return service.getInventoryById(id);
    }

    // ✅ GET inventory by ISBN
    @GetMapping("/books/{isbn}")
    public List<Inventory> getInventoryByISBN(@PathVariable String isbn) {
        return service.getInventoryByISBN(isbn);
    }

    // ✅ POST add inventory
    @PostMapping
    public Inventory addInventory(@RequestBody Inventory inventory) {
        return service.addInventory(inventory);
    }

    // ✅ PUT mark as purchased
    @PutMapping("/{id}/purchase")
    public Inventory markAsPurchased(@PathVariable Integer id) {
        return service.markAsPurchased(id);
    }
}