package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles shopping cart endpoints independently because cart state changes are separate from order creation.
 */
@RestController
public class ShoppingCartController {

    private final IShoppingCartService shoppingCartService;

    public ShoppingCartController(IShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * Returns cart items for one user.
     */
    @GetMapping("/api/v1/cart/{userId}")
    public ResponseEntity<ResponseStructure<PaginatedResponse<ShoppingCartResponseDTO>>> getCartByUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Cart items fetched successfully",
                        PaginationUtils.paginate(shoppingCartService.getByUserId(userId), page, size)
                )
        );
    }

    /**
     * Adds a book to a user's cart.
     */
    @PostMapping("/api/v1/cart/{userId}/items/{isbn}")
    public ResponseEntity<ResponseStructure<ShoppingCartResponseDTO>> addCartItem(
            @PathVariable Integer userId,
            @PathVariable String isbn) {
        ShoppingCartRequestDTO requestDTO = new ShoppingCartRequestDTO();
        requestDTO.setUserId(userId);
        requestDTO.setIsbn(isbn);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(
                        HttpStatus.CREATED.value(),
                        "Cart item created successfully",
                        shoppingCartService.addCart(requestDTO)
                ));
    }

    /**
     * Removes a book from a user's cart.
     */
    @DeleteMapping("/api/v1/cart/{userId}/items/{isbn}")
    public ResponseEntity<ResponseStructure<String>> deleteCartItem(
            @PathVariable Integer userId,
            @PathVariable String isbn) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Cart item deleted successfully",
                        shoppingCartService.delete(userId, isbn)
                )
        );
    }
}
