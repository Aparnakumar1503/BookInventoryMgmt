package com.sprint.BookInventoryMgmt.ordermgmt.controller;

import com.sprint.BookInventoryMgmt.ordermgmt.service.ShoppingCartService;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.ShoppingCartResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService service;

    public ShoppingCartController(ShoppingCartService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ShoppingCartResponseDTO addCart(@RequestBody ShoppingCartRequestDTO requestDTO) {
        return service.addCart(requestDTO);
    }

    @GetMapping("/get")
    public List<ShoppingCartResponseDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        return service.delete(userId);

}}