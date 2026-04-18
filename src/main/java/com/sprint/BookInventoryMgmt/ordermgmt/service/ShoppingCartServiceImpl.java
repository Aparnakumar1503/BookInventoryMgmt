package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.ordermgmt.repository.ShoppingCartRepository;
import com.sprint.BookInventoryMgmt.ordermgmt.exception.ShoppingCartNotFoundException;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.ShoppingCartResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository repo;

    public ShoppingCartServiceImpl(ShoppingCartRepository repo) {
        this.repo = repo;
    }

    @Override
    public ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO dto) {

        ShoppingCart entity = new ShoppingCart();
        entity.setUserId(dto.getUserId());
        entity.setIsbn(dto.getIsbn());

        ShoppingCart saved = repo.save(entity);

        return mapToDTO(saved);
    }

    @Override
    public List<ShoppingCartResponseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public String delete(Long userId) {

        ShoppingCart cart = repo.findById(userId)
                .orElseThrow(() ->
                        new ShoppingCartNotFoundException("Cart not found: " + userId)
                );

        repo.delete(cart);
        return "Cart Deleted Successfully for id: " + userId;
    }

    private ShoppingCartResponseDTO mapToDTO(ShoppingCart entity) {
        ShoppingCartResponseDTO dto = new ShoppingCartResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setIsbn(entity.getIsbn());
        return dto;
    }
}