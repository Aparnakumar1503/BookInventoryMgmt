package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;

import java.util.List;


public interface IShoppingCartService {

        ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO requestDTO);
        List<ShoppingCartResponseDTO> getAll();
        String delete(Integer userId, String isbn); // changed from Long userId
    }