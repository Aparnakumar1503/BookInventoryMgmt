package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;

import java.util.List;



// Service layer interface for ShoppingCart module - defines business operations between controller and repository
public interface IShoppingCartService {

    // Adds a book item to user's cart and returns response DTO
    ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO requestDTO);

    // Retrieves all cart items (used for admin or full cart view)
    List<ShoppingCartResponseDTO> getAll();

    // Retrieves cart items for a specific user based on userId
    List<ShoppingCartResponseDTO> getByUserId(Integer userId);

    // Deletes a cart item using userId and ISBN and returns status message
    String delete(Integer userId, String isbn);
}
