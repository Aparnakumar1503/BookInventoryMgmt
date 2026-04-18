package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.ShoppingCartRepository;
import com.sprint.BookInventoryMgmt.OrderMgmt.Exception.ShoppingCartNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository repo;

    public ShoppingCartServiceImpl(ShoppingCartRepository repo) {
        this.repo = repo;
    }
    @Override
    public ShoppingCart addCart(ShoppingCart cart) {
        return repo.save(cart);
    }
    @Override
    public List<ShoppingCart> getAll() {
        return repo.findAll();
    }
    @Override
    public String delete(Long userId) {

        ShoppingCart cart = repo.findById(userId)
                .orElseThrow(() ->
                        new ShoppingCartNotFoundException("Shopping Cart not found for id: " + userId)
                );

        repo.delete(cart);

        return "Cart Deleted Successfully for id: " + userId;
    }
}