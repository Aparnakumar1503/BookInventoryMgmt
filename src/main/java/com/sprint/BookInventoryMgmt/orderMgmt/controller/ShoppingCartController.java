package com.sprint.BookInventoryMgmt.orderMgmt.controller;

import com.sprint.BookInventoryMgmt.common.ResponseStructure;
import com.sprint.BookInventoryMgmt.orderMgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.service.IShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    IShoppingCartService service;

    public ShoppingCartController(){}


    public ShoppingCartController(IShoppingCartService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseStructure<ShoppingCartResponseDTO> addCart(
            @RequestBody ShoppingCartRequestDTO dto) {

        ShoppingCartResponseDTO response = service.addCart(dto);

        return new ResponseStructure<>(
                201,
                "Cart added successfully",
                response
        );
    }

    @GetMapping("/get")
    public ResponseStructure<List<ShoppingCartResponseDTO>> getAll() {

        List<ShoppingCartResponseDTO> list = service.getAll();

        return new ResponseStructure<>(
                200,
                "Fetched successfully",
                list
        );
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseStructure<String> delete(@PathVariable Long userId) {

        service.delete(userId);

        return new ResponseStructure<>(
                200,
                "Cart deleted successfully",
                null
        );
    }
}