package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService service;

    public ShoppingCartController(IShoppingCartService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseStructure<ShoppingCartResponseDTO> addCart(
            @RequestBody ShoppingCartRequestDTO requestDTO) {
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Item added to cart",
                service.addCart(requestDTO)
        );
    }

    @GetMapping
    public ResponseStructure<List<ShoppingCartResponseDTO>> getAll() {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Cart fetched successfully",
                service.getAll()
        );
    }

    @GetMapping("/{userId}")
    public ResponseStructure<List<ShoppingCartResponseDTO>> getCartByUser(
            @PathVariable Integer userId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "User cart fetched successfully",
                service.getByUserId(userId)
        );
    }

    @DeleteMapping("/{userId}/{isbn}")
    public ResponseStructure<String> delete(@PathVariable Integer userId,
                                            @PathVariable String isbn) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Cart item deleted successfully",
                service.delete(userId, isbn)
        );
    }
}
