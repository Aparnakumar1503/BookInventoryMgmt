package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository repo;

    public ShoppingCartService(ShoppingCartRepository repo) {
        this.repo = repo;
    }

    public ShoppingCart addCart(ShoppingCart cart) {
        return repo.save(cart);
    }

    public List<ShoppingCart> getAll() {
        return repo.findAll();
    }

    public String delete(Long userId) {
        repo.deleteById(userId);
        return "Deleted Successfully";
    }
}