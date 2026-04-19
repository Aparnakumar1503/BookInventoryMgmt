package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.ShoppingCartResponseDTO;

import java.util.List;


public interface IShoppingCartService {

        ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO requestDTO);
        List<ShoppingCartResponseDTO> getAll();
        String delete(Integer userId, String isbn); // changed from Long userId
    }