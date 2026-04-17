package com.sprint.BookInventoryMgmt.InventoryMgmt.controller;

import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.*;
import com.sprint.BookInventoryMgmt.InventoryMgmt.responsedto.*;
import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.Inventory;
import com.sprint.BookInventoryMgmt.InventoryMgmt.service.InventoryService;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

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
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> save(@RequestBody InventoryRequestDTO dto) {

        Inventory saved = service.saveInventory(InventoryMapper.toInventoryEntity(dto));

        ResponseStructure<InventoryResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Inventory created successfully");
        response.setData(InventoryMapper.toInventoryResponse(saved));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<InventoryResponseDTO>>> getAll() {

        List<InventoryResponseDTO> list = service.getAllInventory()
                .stream()
                .map(InventoryMapper::toInventoryResponse)
                .toList();

        ResponseStructure<List<InventoryResponseDTO>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Inventory fetched successfully");
        response.setData(list);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> getById(@PathVariable Integer id) {

        InventoryResponseDTO dto =
                InventoryMapper.toInventoryResponse(service.getById(id));

        ResponseStructure<InventoryResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Inventory fetched successfully");
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<ResponseStructure<List<InventoryResponseDTO>>> getByIsbn(@PathVariable String isbn) {

        List<InventoryResponseDTO> list = service.getByIsbn(isbn)
                .stream()
                .map(InventoryMapper::toInventoryResponse)
                .toList();

        ResponseStructure<List<InventoryResponseDTO>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Inventory fetched by ISBN successfully");
        response.setData(list);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/purchase")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> purchase(@PathVariable Integer id) {

        InventoryResponseDTO dto =
                InventoryMapper.toInventoryResponse(service.markAsPurchased(id));

        ResponseStructure<InventoryResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Inventory marked as purchased");
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteInventory(@PathVariable Integer id) {

        ResponseStructure<String> response = service.deleteInventory(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}