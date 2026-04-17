package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.Inventory;
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

    public Inventory saveInventory(Inventory inventory) {
        return repository.save(inventory);
    }

    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    public Inventory getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
    }

    public List<Inventory> getByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

    public Inventory markAsPurchased(Integer id) {
        Inventory inv = getById(id);
        inv.setPurchased(true);
        return repository.save(inv);
    }

    @Override
    public ResponseStructure<String> deleteInventory(Integer id) {

        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));

        repository.delete(inventory);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Inventory deleted successfully");
        response.setData("Deleted inventory id: " + id);

        return response;
    }
}