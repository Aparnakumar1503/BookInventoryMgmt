package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IShoppingCartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

// Marks this class as a REST controller to handle HTTP requests related to shopping cart operations
@RestController

// Defines the base URL path for all shopping cart APIs
@RequestMapping("/api/v1/cart")

// Enables validation support for request body and path variables
@Validated
public class ShoppingCartController {

    // Injects ShoppingCart service implementation using Spring dependency injection
    @Autowired
    private IShoppingCartService service;

    // Constructor-based dependency injection for better testing and loose coupling
    public ShoppingCartController(IShoppingCartService service) {
        this.service = service;
    }

    // API endpoint to add an item to the shopping cart
    @PostMapping
    public ResponseStructure<ShoppingCartResponseDTO> addCart(
            @Valid @RequestBody ShoppingCartRequestDTO requestDTO) {

        // Validates request data and forwards it to service layer for cart creation
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Item added to cart",
                service.addCart(requestDTO)
        );

        // Returns a standardized response after successfully adding item to cart
    }

    // API endpoint to fetch all cart items (admin or full cart view use case)
    @GetMapping
    public ResponseStructure<List<ShoppingCartResponseDTO>> getAll() {

        // Retrieves all cart records from service layer
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Cart fetched successfully",
                service.getAll()
        );

        // Returns complete cart list in a structured response format
    }

    // API endpoint to fetch cart items of a specific user
    @GetMapping("/{userId}")
    public ResponseStructure<List<ShoppingCartResponseDTO>> getCartByUser(
            @PathVariable @Positive(message = "User ID must be greater than 0") Integer userId) {

        // Extracts userId from URL and validates it before processing
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "User cart fetched successfully",
                service.getByUserId(userId)
        );

        // Returns only the cart items belonging to the given user
    }

    // API endpoint to delete a specific item from user's cart using userId and ISBN
    @DeleteMapping("/{userId}/{isbn}")
    public ResponseStructure<String> delete(
            @PathVariable @Positive(message = "User ID must be greater than 0") Integer userId,
            @PathVariable @NotBlank(message = "ISBN cannot be blank") String isbn) {

        // Validates input parameters before removing item from cart
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Cart item deleted successfully",
                service.delete(userId, isbn)
        );

        // Confirms successful deletion of item from shopping cart
    }
}