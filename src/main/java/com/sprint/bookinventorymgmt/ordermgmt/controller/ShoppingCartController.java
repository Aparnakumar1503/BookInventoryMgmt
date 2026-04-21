package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final IShoppingCartService service;

    public ShoppingCartController(IShoppingCartService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseStructure<ShoppingCartResponseDTO>> addCart(
            @RequestBody ShoppingCartRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED.value(),
                                "Cart item created successfully",
                                service.addCart(requestDTO)
                        )
                );
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseStructure<List<ShoppingCartResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Cart items fetched successfully",
                        service.getAll()
                )
        );
    }

    @DeleteMapping("/delete/{userId}/{isbn}")
    public ResponseEntity<ResponseStructure<String>> delete(
            @PathVariable Integer userId,
            @PathVariable String isbn) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Cart item deleted successfully",
                        service.delete(userId, isbn)
                )
        );
    }
}
