package com.sprint.BookInventoryMgmt.OrderMgmt.Controller;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.OrderMgmt.Service.ShoppingCartServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartServiceImpl service;

    public ShoppingCartController(ShoppingCartServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ShoppingCart addCart(@RequestBody ShoppingCart cart) {
        return service.addCart(cart);
    }

    @GetMapping("/get")
    public List<ShoppingCart> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        service.delete(userId);
        return "Deleted Successfully";
    }
}