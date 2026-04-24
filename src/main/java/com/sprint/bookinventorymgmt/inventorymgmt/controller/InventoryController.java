package com.sprint.bookinventorymgmt.inventorymgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.InventoryMapper;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.InventoryRequestDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.responsedto.InventoryResponseDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryController {

    private final IInventoryService inventoryService;

    public InventoryController(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Returns all inventory records.
     */
    @GetMapping("/api/v1/inventory")
    public ResponseEntity<ResponseStructure<PaginatedResponse<InventoryResponseDTO>>> getAllInventory(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<InventoryResponseDTO> response = inventoryService.getAllInventory().stream()
                .map(InventoryMapper::toInventoryResponse)
                .toList();

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Inventory fetched successfully",
                        PaginationUtils.paginate(response, page, size)
                )
        );
    }

    /**
     * Returns one inventory record by identifier.
     */
    @GetMapping("/api/v1/inventory/{inventoryId}")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> getInventoryById(@PathVariable Integer inventoryId) {
        InventoryResponseDTO dto = InventoryMapper.toInventoryResponse(inventoryService.getById(inventoryId));
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Inventory fetched successfully",
                        dto
                )
        );
    }

    /**
     * Returns inventory records for one ISBN.
     */
    @GetMapping("/api/v1/inventory/books/{isbn}")
    public ResponseEntity<ResponseStructure<PaginatedResponse<InventoryResponseDTO>>> getInventoryByIsbn(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<InventoryResponseDTO> response = inventoryService.getByIsbn(isbn).stream()
                .map(InventoryMapper::toInventoryResponse)
                .toList();

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Inventory fetched by ISBN successfully",
                        PaginationUtils.paginate(response, page, size)
                )
        );
    }

    /**
     * Returns only the available inventory copies for one ISBN using the custom query endpoint.
     */
    @GetMapping("/api/v1/inventory/books/{isbn}/available")
    public ResponseEntity<ResponseStructure<PaginatedResponse<InventoryResponseDTO>>> getAvailableInventoryByIsbn(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<InventoryResponseDTO> response = inventoryService.getAvailableByIsbn(isbn).stream()
                .map(InventoryMapper::toInventoryResponse)
                .toList();

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Available inventory fetched successfully",
                        PaginationUtils.paginate(response, page, size)
                )
        );
    }

    /**
     * Creates a new inventory record.
     */
    @PostMapping("/api/v1/inventory")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> saveInventory(
            @Valid @RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBuilder.success(
                        HttpStatus.CREATED.value(),
                        "Inventory created successfully",
                        InventoryMapper.toInventoryResponse(
                                inventoryService.saveInventory(InventoryMapper.toInventoryEntity(dto))
                        )
                )
        );
    }

    /**
     * Updates one inventory record by identifier.
     */
    @PutMapping("/api/v1/inventory/{inventoryId}")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> updateInventory(
            @PathVariable Integer inventoryId,
            @Valid @RequestBody InventoryRequestDTO dto) {
        ResponseStructure<com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory> result =
                inventoryService.updateInventory(inventoryId, InventoryMapper.toInventoryEntity(dto));

        return ResponseEntity.status(result.getStatusCode()).body(
                ResponseBuilder.success(
                        result.getStatusCode(),
                        result.getMessage(),
                        InventoryMapper.toInventoryResponse(result.getData())
                )
        );
    }

    /**
     * Marks an inventory item as purchased.
     */
    @PutMapping("/api/v1/inventory/{inventoryId}/purchase")
    public ResponseEntity<ResponseStructure<InventoryResponseDTO>> purchaseInventory(@PathVariable Integer inventoryId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Inventory marked as purchased",
                        InventoryMapper.toInventoryResponse(inventoryService.markAsPurchased(inventoryId))
                )
        );
    }

    /**
     * Deletes one inventory record by identifier.
     */
    @DeleteMapping("/api/v1/inventory/{inventoryId}")
    public ResponseEntity<ResponseStructure<String>> deleteInventory(@PathVariable Integer inventoryId) {
        ResponseStructure<String> result = inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }
}
