package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCartId;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.*;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IShoppingCartRepository;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    private final IShoppingCartRepository repo;

    public ShoppingCartServiceImpl(IShoppingCartRepository repo) {
        this.repo = repo;
    }

    @Override
    public ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO dto) {

        if (dto == null || dto.getUserId() == null || dto.getIsbn() == null || dto.getIsbn().isBlank()) {
            throw new EmptyCartException("UserId and isbn required");
        }

        ShoppingCart existing = repo.findByUserIdAndIsbn(dto.getUserId(), dto.getIsbn());
        if (existing != null) {
            throw new DuplicateCartItemException("Item already exists");
        }

        ShoppingCart entity = new ShoppingCart(dto.getUserId(), dto.getIsbn());

        ShoppingCart saved = repo.save(entity);

        return mapToDTO(saved);
    }

    @Override
    public List<ShoppingCartResponseDTO> getAll() {

        List<ShoppingCart> list = repo.findAll();
        List<ShoppingCartResponseDTO> response = new ArrayList<>();

        for (ShoppingCart c : list) {
            response.add(mapToDTO(c));
        }

        return response;
    }

    @Override
    public List<ShoppingCartResponseDTO> getByUserId(Integer userId) {

        List<ShoppingCart> list = repo.findByIdUserId(userId);
        List<ShoppingCartResponseDTO> response = new ArrayList<>();

        for (ShoppingCart c : list) {
            response.add(mapToDTO(c));
        }

        return response;
    }

    @Override
    public String delete(Integer userId, String isbn) {

        ShoppingCartId id = new ShoppingCartId(userId, isbn);

        ShoppingCart cart = repo.findById(id)
                .orElseThrow(() ->
                        new ShoppingCartNotFoundException("Cart not found"));

        repo.delete(cart);

        return "Cart Deleted Successfully for userId: " + userId + " isbn: " + isbn;
    }

    private ShoppingCartResponseDTO mapToDTO(ShoppingCart entity) {

        ShoppingCartResponseDTO dto = new ShoppingCartResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setIsbn(entity.getIsbn());

        return dto;
    }
}