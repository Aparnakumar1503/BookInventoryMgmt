package com.sprint.BookInventoryMgmt.OrderMgmt.Controller;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.ShoppingCartRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartRepository repo;

    public ShoppingCartController(ShoppingCartRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/add")
    public ShoppingCart addCart(@RequestBody ShoppingCart cart) {
        return repo.save(cart);
    }

    @GetMapping("/get")
    public List<ShoppingCart> getAll() {
        return repo.findAll();
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        repo.deleteById(userId);
        return "Deleted Successfully";
    }
}