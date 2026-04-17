package com.sprint.BookInventoryMgmt.OrderMgmt.Service;


import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.ShoppingCartResponseDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.ShoppingCartRepository;
import com.sprint.BookInventoryMgmt.OrderMgmt.Exception.ShoppingCartNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository repo;

    public ShoppingCartServiceImpl(ShoppingCartRepository repo) {
        this.repo = repo;
    }

    @Override
    public ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO dto) {

        // DTO → Entity
        ShoppingCart entity = new ShoppingCart();
        entity.setUserId(dto.getUserId());
        entity.setIsbn(dto.getIsbn());

        ShoppingCart saved = repo.save(entity);

        // Entity → DTO
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