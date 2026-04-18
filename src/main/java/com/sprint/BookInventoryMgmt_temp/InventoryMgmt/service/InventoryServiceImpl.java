package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.Inventory;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.*;
import com.sprint.BookInventoryMgmt.InventoryMgmt.repository.InventoryRepository;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;

    // ✅ CREATE
    @Override
    public Inventory saveInventory(Inventory inventory) {

        if (inventory.getIsbn() == null || inventory.getIsbn().isBlank()
                || inventory.getRanks() == null) {
            throw new InvalidInventoryDataException("ISBN and Rank cannot be null or empty");
        }

        // Duplicate check
        List<Inventory> existing = repository.findByIsbn(inventory.getIsbn());

        boolean duplicate = existing.stream()
                .anyMatch(inv ->
                        inv.getRanks().equals(inventory.getRanks())
                                && Boolean.FALSE.equals(inv.getPurchased()));

        if (duplicate) {
            throw new DuplicateInventoryException(
                    "Inventory already exists for this ISBN and rank and is not purchased");
        }

        try {
            return repository.save(inventory);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to save inventory");
        }
    }

    // ✅ GET ALL
    @Override
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    // ✅ GET BY ID
    @Override
    public Inventory getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found with id: " + id));
    }

    // ✅ GET BY ISBN
    @Override
    public List<Inventory> getByIsbn(String isbn) {

        if (isbn == null || isbn.isBlank()) {
            throw new InvalidInventoryDataException("ISBN cannot be empty");
        }

        List<Inventory> list = repository.findByIsbn(isbn);

        if (list.isEmpty()) {
            throw new InventoryNotFoundException("No inventory found for ISBN: " + isbn);
        }

        return list;
    }

    // ✅ PURCHASE
    @Override
    public Inventory markAsPurchased(Integer id) {

        Inventory inv = repository.findById(id)
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found with id: " + id));

        if (Boolean.TRUE.equals(inv.getPurchased())) {
            throw new InventoryPurchaseException("This inventory is already purchased");
        }

        inv.setPurchased(true);

        try {
            return repository.save(inv);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update purchase status");
        }
    }

    // ✅ DELETE
    @Override
    public ResponseStructure<String> deleteInventory(Integer id) {

        Inventory inventory = repository.findById(id)
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found with id: " + id));

        try {
            repository.delete(inventory);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete inventory");
        }

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Inventory deleted successfully",
                "Deleted inventory id: " + id
        );
    }

    // ✅ UPDATE (IMPORTANT FIX APPLIED)
    @Override
    public ResponseStructure<Inventory> updateInventory(Integer id, Inventory updated) {

        if (updated.getIsbn() == null || updated.getIsbn().isBlank()
                || updated.getRanks() == null) {
            throw new InvalidInventoryDataException("ISBN and Rank cannot be null or empty");
        }

        Inventory existing = repository.findById(id)
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found with id: " + id));

        // Update fields
        existing.setIsbn(updated.getIsbn());
        existing.setRanks(updated.getRanks());

        // ✅ NULL SAFE FIX (IMPORTANT)
        existing.setPurchased(
                updated.getPurchased() != null
                        ? updated.getPurchased()
                        : existing.getPurchased()
        );

        Inventory saved;

        try {
            saved = repository.save(existing);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update inventory");
        }

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Inventory updated successfully",
                saved
        );
    }
}