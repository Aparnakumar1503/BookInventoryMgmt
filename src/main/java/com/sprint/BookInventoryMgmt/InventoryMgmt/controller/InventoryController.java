package com.sprint.BookInventoryMgmt.InventoryMgmt.controller;

import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.*;
import com.sprint.BookInventoryMgmt.InventoryMgmt.responsedto.*;
import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.Inventory;
import com.sprint.BookInventoryMgmt.InventoryMgmt.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> save(@RequestBody InventoryRequestDTO dto) {
        Inventory saved = service.saveInventory(InventoryMapper.toInventoryEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(InventoryMapper.toInventoryResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAll() {
        return ResponseEntity.ok(
                service.getAllInventory().stream()
                        .map(InventoryMapper::toInventoryResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                InventoryMapper.toInventoryResponse(service.getById(id))
        );
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<List<InventoryResponseDTO>> getByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(
                service.getByIsbn(isbn).stream()
                        .map(InventoryMapper::toInventoryResponse)
                        .toList()
        );
    }

    @PutMapping("/{id}/purchase")
    public ResponseEntity<InventoryResponseDTO> purchase(@PathVariable Integer id) {
        return ResponseEntity.ok(
                InventoryMapper.toInventoryResponse(service.markAsPurchased(id))
        );
    }
}