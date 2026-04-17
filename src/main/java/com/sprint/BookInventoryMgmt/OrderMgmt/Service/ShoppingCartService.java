package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.dto.requestdto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.dto.responsedto.ShoppingCartResponseDTO;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO cart);

    List<ShoppingCartResponseDTO> getAll();

    String delete(Long userId);
}