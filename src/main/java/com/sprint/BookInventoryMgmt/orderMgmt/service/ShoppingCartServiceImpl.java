package com.sprint.BookInventoryMgmt.orderMgmt.service;


import com.sprint.BookInventoryMgmt.orderMgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.orderMgmt.repository.IShoppingCartRepository;
import com.sprint.BookInventoryMgmt.orderMgmt.exceptions.ShoppingCartNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    @Autowired
    IShoppingCartRepository repo;

    public ShoppingCartServiceImpl(){

    }

    public ShoppingCartServiceImpl(IShoppingCartRepository repo) {

        this.repo = repo;
    }

    @Override
    public ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO dto) {

        ShoppingCart entity = new ShoppingCart();
        entity.setUserId(dto.getUserId());
        entity.setIsbn(dto.getIsbn());

        ShoppingCart saved = repo.save(entity);

        ShoppingCartResponseDTO response = new ShoppingCartResponseDTO();
        response.setUserId(saved.getUserId());
        response.setIsbn(saved.getIsbn());

        return response;
    }

    @Override
    public List<ShoppingCartResponseDTO> getAll() {

        List<ShoppingCart> carts = repo.findAll();
        List<ShoppingCartResponseDTO> responseList = new ArrayList<>();

        for (ShoppingCart cart : carts) {
            ShoppingCartResponseDTO dto = new ShoppingCartResponseDTO();
            dto.setUserId(cart.getUserId());
            dto.setIsbn(cart.getIsbn());

            responseList.add(dto);
        }

        return responseList;
    }

    @Override
    public String delete(Long userId) {

        ShoppingCart cart = repo.findById(userId)
                .orElseThrow(() ->
                        new ShoppingCartNotFoundException("Shopping Cart not found for id: " + userId)
                );

        repo.delete(cart);

        return "Cart Deleted Successfully for id: " + userId;
    }
}