package com.sprint.bookinventorymgmt.inventorymgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.InventoryMapper;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.InventoryRequestDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.responsedto.InventoryResponseDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import com.sprint.bookinventorymgmt.inventorymgmt.service.IInventoryService;
import com.sprint.bookinventorymgmt.common.ResponseStructure;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private IInventoryService service;

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> save(
            @Valid @RequestBody InventoryRequestDTO dto) {

        Inventory saved = service.saveInventory(
                InventoryMapper.toInventoryEntity(dto)
        );

        ResponseStructure<InventoryResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Inventory created successfully");
        response.setData(InventoryMapper.toInventoryResponse(saved));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<ResponseStructure<PaginatedResponse<InventoryResponseDTO>>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        List<Inventory> inventories = service.getAllInventory();
        List<InventoryResponseDTO> list = new java.util.ArrayList<>();
        for (Inventory inventory : inventories) {
            list.add(InventoryMapper.toInventoryResponse(inventory));
        }

        ResponseStructure<PaginatedResponse<InventoryResponseDTO>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Inventory fetched successfully");
        response.setData(PaginationUtils.paginate(list, page, size));

        return ResponseEntity.ok(response);
    }

    // ✅ GET BY ID
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

    // ✅ GET BY ISBN
    @GetMapping("/books/{isbn}")
    public ResponseEntity<ResponseStructure<PaginatedResponse<InventoryResponseDTO>>> getByIsbn(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        List<Inventory> inventories = service.getByIsbn(isbn);
        List<InventoryResponseDTO> list = new java.util.ArrayList<>();
        for (Inventory inventory : inventories) {
            list.add(InventoryMapper.toInventoryResponse(inventory));
        }

        ResponseStructure<PaginatedResponse<InventoryResponseDTO>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Inventory fetched by ISBN successfully");
        response.setData(PaginationUtils.paginate(list, page, size));

        return ResponseEntity.ok(response);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody InventoryRequestDTO dto) {

        Inventory updated = InventoryMapper.toInventoryEntity(dto);

        ResponseStructure<Inventory> result =
                service.updateInventory(id, updated);

        ResponseStructure<InventoryResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(result.getStatusCode());
        response.setMessage(result.getMessage());
        response.setData(InventoryMapper.toInventoryResponse(result.getData()));

        return ResponseEntity.ok(response);
    }

    // ✅ PURCHASE
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

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteInventory(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteInventory(id));
    }
}
