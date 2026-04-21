package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCartId;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.DuplicateCartItemException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.EmptyCartException;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IShoppingCartRepository;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.ShoppingCartNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import org.springframework.stereotype.Service;

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
            throw new EmptyCartException("UserId and isbn are required to add an item to the cart");
        }

        ShoppingCart existingCartItem = repo.findByUserIdAndIsbn(dto.getUserId(), dto.getIsbn());
        if (existingCartItem != null) {
            throw new DuplicateCartItemException(
                    "Cart item already exists for userId: " + dto.getUserId() + " isbn: " + dto.getIsbn());
        }

        ShoppingCart entity = new ShoppingCart(dto.getUserId(), dto.getIsbn());

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
    public List<ShoppingCartResponseDTO> getByUserId(Integer userId) {
        return repo.findByIdUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public String delete(Integer userId, String isbn) {
        // composite key — need both userId and isbn to identify a cart item
        ShoppingCartId id = new ShoppingCartId(userId, isbn);

        ShoppingCart cart = repo.findById(id)
                .orElseThrow(() ->
                        new ShoppingCartNotFoundException("Cart not found for userId: " + userId + " isbn: " + isbn)
                );

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
