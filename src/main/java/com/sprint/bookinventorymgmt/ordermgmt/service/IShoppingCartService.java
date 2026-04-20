package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;

import java.util.List;

public interface IShoppingCartService {

    ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO requestDTO);

    List<ShoppingCartResponseDTO> getAll();

    // ✅ NEW: Get cart by user
    List<ShoppingCartResponseDTO> getCartByUser(Integer userId);

    // ✅ EXISTING
    String delete(Integer userId, String isbn);

    // ✅ NEW: Checkout
    String checkout(Integer userId);
}