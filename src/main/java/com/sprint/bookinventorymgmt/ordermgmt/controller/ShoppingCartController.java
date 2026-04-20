package com.sprint.BookInventoryMgmt.ordermgmt.controller;

import com.sprint.BookInventoryMgmt.common.ResponseStructure;
import com.sprint.BookInventoryMgmt.ordermgmt.service.IShoppingCartService;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final IShoppingCartService service;

    public ShoppingCartController(IShoppingCartService service) {
        this.service = service;
    }

    // ✅ ADD TO CART
    @PostMapping("/add")
    public ResponseStructure<ShoppingCartResponseDTO> addCart(
            @RequestBody ShoppingCartRequestDTO requestDTO) {

        ShoppingCartResponseDTO response = service.addCart(requestDTO);

        return ResponseStructure.of(200, "Item added to cart", response);
    }

    // ✅ GET ALL CART ITEMS
    @GetMapping("/get")
    public ResponseStructure<List<ShoppingCartResponseDTO>> getAll() {

        List<ShoppingCartResponseDTO> list = service.getAll();

        return ResponseStructure.of(200, "Cart fetched successfully", list);
    }

    // ✅ GET CART BY USER
    @GetMapping("/user/{userId}")
    public ResponseStructure<List<ShoppingCartResponseDTO>> getCartByUser(
            @PathVariable Integer userId) {

        List<ShoppingCartResponseDTO> list = service.getCartByUser(userId);

        return ResponseStructure.of(200, "User cart fetched successfully", list);
    }

    // ✅ DELETE ITEM
    @DeleteMapping("/delete/{userId}/{isbn}")
    public ResponseStructure<String> delete(@PathVariable Integer userId,
                                            @PathVariable String isbn) {

        String msg = service.delete(userId, isbn);

        return ResponseStructure.of(200, "Cart item deleted successfully", msg);
    }

    // ✅ CHECKOUT
    @PostMapping("/checkout/{userId}")
    public ResponseStructure<String> checkout(@PathVariable Integer userId) {

        String msg = service.checkout(userId);

        return ResponseStructure.of(200, "Checkout successful", msg);
    }
}