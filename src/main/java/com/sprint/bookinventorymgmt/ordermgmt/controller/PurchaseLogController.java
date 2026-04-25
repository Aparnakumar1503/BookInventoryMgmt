package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IPurchaseLogService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


// Marks this class as a REST API controller that handles HTTP requests and returns JSON responses
@RestController

// Defines the base URL path for all order-related APIs
@RequestMapping("/api/v1/orders")

// Enables validation for method parameters like @PathVariable and @RequestBody
@Validated
public class PurchaseLogController {

    // Injects the Purchase Log service implementation automatically by Spring
    @Autowired
    private IPurchaseLogService service;

    // Constructor-based dependency injection for better testability and clean design
    public PurchaseLogController(IPurchaseLogService service) {
        this.service = service;
    }

    // API endpoint to create a new purchase/order
    @PostMapping
    public ResponseStructure<PurchaseLogResponseDTO> addPurchase(
            @Valid @RequestBody PurchaseLogRequestDTO requestDTO) {

        // Validates incoming request and forwards it to service layer for processing
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Purchase added successfully",
                service.addPurchase(requestDTO)
        );

        // Returns a standardized success response after creating purchase
    }

    // API endpoint to fetch all purchase records
    @GetMapping
    public ResponseStructure<List<PurchaseLogResponseDTO>> getAll() {

        // Calls service layer to retrieve all purchases
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Purchases fetched successfully",
                service.getAll()
        );

        // Wraps response in a consistent API response structure
    }

    // API endpoint to fetch purchase history of a specific user
    @GetMapping("/user/{userId}")
    public ResponseStructure<List<PurchaseLogResponseDTO>> getByUserId(
            @PathVariable @Positive(message = "User ID must be greater than 0") Integer userId) {

        // Extracts userId from URL and validates it before processing
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Purchases fetched successfully for user",
                service.getByUserId(userId)
        );

        // Returns all purchases belonging to a specific user
    }

    // API endpoint to delete a purchase based on user and inventory combination
    @DeleteMapping("/{userId}/{inventoryId}")
    public ResponseStructure<String> delete(
            @PathVariable @Positive(message = "User ID must be greater than 0") Integer userId,
            @PathVariable @Positive(message = "Inventory ID must be greater than 0") Integer inventoryId) {

        // Takes both IDs from URL and ensures they are valid before deletion
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Purchase deleted successfully",
                service.delete(userId, inventoryId)
        );

        // Confirms successful deletion of purchase record
    }
}