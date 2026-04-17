package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.ShoppingCartResponseDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {

    ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO cart);

    List<ShoppingCartResponseDTO> getAll();

    String delete(Long userId);
}