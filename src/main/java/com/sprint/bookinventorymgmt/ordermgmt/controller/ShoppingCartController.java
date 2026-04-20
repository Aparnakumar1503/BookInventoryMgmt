package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.ordermgmt.service.IShoppingCartService;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
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
    public ShoppingCartResponseDTO addCart(@RequestBody ShoppingCartRequestDTO requestDTO) {
        return service.addCart(requestDTO);
    }

    @GetMapping("/get")
    public List<ShoppingCartResponseDTO> getAll() {
        return service.getAll();
    }

    // composite key — need both userId and isbn to delete
    @DeleteMapping("/delete/{userId}/{isbn}")
    public String delete(@PathVariable Integer userId,
                         @PathVariable String isbn) {
        return service.delete(userId, isbn);
    }
}