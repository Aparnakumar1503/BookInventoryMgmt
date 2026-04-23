package com.sprint.bookinventorymgmt.inventorymgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.InventoryMapper;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.InventoryRequestDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.responsedto.InventoryResponseDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import com.sprint.bookinventorymgmt.inventorymgmt.service.IInventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final IInventoryService service;

    public InventoryController(IInventoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> save(
            @Valid @RequestBody InventoryRequestDTO dto) {
        Inventory saved = service.saveInventory(InventoryMapper.toInventoryEntity(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBuilder.success(
                        HttpStatus.CREATED.value(),
                        "Inventory created successfully",
                        InventoryMapper.toInventoryResponse(saved)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<PaginatedResponse<InventoryResponseDTO>>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<Inventory> inventories = service.getAllInventory();
        List<InventoryResponseDTO> list = new ArrayList<>();
        for (Inventory inventory : inventories) {
            list.add(InventoryMapper.toInventoryResponse(inventory));
        }

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Inventory fetched successfully",
                        PaginationUtils.paginate(list, page, size)
                )
        );
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> getById(@PathVariable Integer inventoryId) {
        InventoryResponseDTO dto = InventoryMapper.toInventoryResponse(service.getById(inventoryId));

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Inventory fetched successfully",
                        dto
                )
        );
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<ResponseStructure<PaginatedResponse<InventoryResponseDTO>>> getByIsbn(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<Inventory> inventories = service.getByIsbn(isbn);
        List<InventoryResponseDTO> list = new ArrayList<>();
        for (Inventory inventory : inventories) {
            list.add(InventoryMapper.toInventoryResponse(inventory));
        }

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Inventory fetched by ISBN successfully",
                        PaginationUtils.paginate(list, page, size)
                )
        );
    }

    @GetMapping("/books/{isbn}/available")
    public ResponseEntity<ResponseStructure<PaginatedResponse<InventoryResponseDTO>>> getAvailableByIsbn(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<Inventory> inventories = service.getAvailableByIsbn(isbn);
        List<InventoryResponseDTO> list = new ArrayList<>();
        for (Inventory inventory : inventories) {
            list.add(InventoryMapper.toInventoryResponse(inventory));
        }

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Available inventory fetched successfully by ISBN",
                        PaginationUtils.paginate(list, page, size)
                )
        );
    }

    @PutMapping("/{inventoryId}")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> update(
            @PathVariable Integer inventoryId,
            @Valid @RequestBody InventoryRequestDTO dto) {
        Inventory updated = InventoryMapper.toInventoryEntity(dto);
        ResponseStructure<Inventory> result = service.updateInventory(inventoryId, updated);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        result.getStatusCode(),
                        result.getMessage(),
                        InventoryMapper.toInventoryResponse(result.getData())
                )
        );
    }

    @PutMapping("/{inventoryId}/purchase")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> purchase(@PathVariable Integer inventoryId) {
        InventoryResponseDTO dto = InventoryMapper.toInventoryResponse(service.markAsPurchased(inventoryId));

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Inventory marked as purchased",
                        dto
                )
        );
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<ResponseStructure<String>> deleteInventory(@PathVariable Integer inventoryId) {
        return ResponseEntity.ok(service.deleteInventory(inventoryId));
    }
}
