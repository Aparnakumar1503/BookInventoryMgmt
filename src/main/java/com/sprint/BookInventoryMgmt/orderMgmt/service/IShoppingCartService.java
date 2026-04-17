package com.sprint.BookInventoryMgmt.orderMgmt.service;

import com.sprint.BookInventoryMgmt.orderMgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.dto.responseDto.ShoppingCartResponseDTO;

import java.util.List;

public interface IShoppingCartService {

    ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO cart);

    List<ShoppingCartResponseDTO> getAll();

    String delete(Long userId);
}