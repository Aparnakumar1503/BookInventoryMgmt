package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;

import java.util.List;


public interface IShoppingCartService {

        ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO requestDTO);
        List<ShoppingCartResponseDTO> getAll();
        String delete(Integer userId, String isbn); // changed from Long userId
    }