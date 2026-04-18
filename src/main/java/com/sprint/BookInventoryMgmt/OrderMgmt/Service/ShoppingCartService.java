package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {

    ShoppingCart addCart(ShoppingCart cart);

    List<ShoppingCart> getAll();

    String delete(Long userId);
}