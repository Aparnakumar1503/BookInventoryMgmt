package com.sprint.BookInventoryMgmt.OrderMgmt.Controller;

import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.ShoppingCartResponseDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.OrderMgmt.Service.ShoppingCartService;
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
    public ShoppingCartResponseDTO addCart(@RequestBody ShoppingCartRequestDTO dto) {
        return service.addCart(dto);
    }

    @GetMapping("/get")
    public List<ShoppingCartResponseDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        service.delete(userId);
        return "Deleted Successfully";
    }
}