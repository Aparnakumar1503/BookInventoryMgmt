package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.ShoppingCartResponseDTO;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO cart);

    List<ShoppingCartResponseDTO> getAll();

    String delete(Long userId);
}