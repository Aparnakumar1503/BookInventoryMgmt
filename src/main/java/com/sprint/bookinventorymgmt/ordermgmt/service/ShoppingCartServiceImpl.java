package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.ordermgmt.entity.ShoppingCartId;
import com.sprint.BookInventoryMgmt.ordermgmt.repository.IShoppingCartRepository;
import com.sprint.BookInventoryMgmt.ordermgmt.exceptions.ShoppingCartNotFoundException;
import com.sprint.BookInventoryMgmt.ordermgmt.exceptions.EmptyCartException;
import com.sprint.BookInventoryMgmt.ordermgmt.exceptions.DuplicateCartItemException;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    private final IShoppingCartRepository repo;

    public ShoppingCartServiceImpl(IShoppingCartRepository repo) {
        this.repo = repo;
    }

    // ✅ ADD TO CART
    @Override
    public ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO dto) {

        ShoppingCart existing = repo.findByUserIdAndIsbn(dto.getUserId(), dto.getIsbn());

        if (existing != null) {
            throw new DuplicateCartItemException(
                    "Item already exists in cart for userId: " + dto.getUserId()
            );
        }

        ShoppingCart entity = new ShoppingCart(dto.getUserId(), dto.getIsbn());
        ShoppingCart saved = repo.save(entity);

        return mapToDTO(saved);
    }

    // ✅ GET ALL
    @Override
    public List<ShoppingCartResponseDTO> getAll() {

        List<ShoppingCart> carts = repo.findAll();

        if (carts.isEmpty()) {
            throw new EmptyCartException("No items found in cart");
        }

        List<ShoppingCartResponseDTO> list = new ArrayList<>();

        for (ShoppingCart cart : carts) {
            list.add(mapToDTO(cart));
        }

        return list;
    }

    // ✅ GET CART BY USER
    public List<ShoppingCartResponseDTO> getCartByUser(Integer userId) {

        List<ShoppingCart> carts = repo.findByIdUserId(userId);

        if (carts.isEmpty()) {
            throw new EmptyCartException("Cart is empty for userId: " + userId);
        }

        List<ShoppingCartResponseDTO> list = new ArrayList<>();

        for (ShoppingCart cart : carts) {
            list.add(mapToDTO(cart));
        }

        return list;
    }

    // ✅ DELETE
    @Override
    public String delete(Integer userId, String isbn) {

        ShoppingCartId id = new ShoppingCartId(userId, isbn);

        ShoppingCart cart = repo.findById(id)
                .orElseThrow(() ->
                        new ShoppingCartNotFoundException(
                                "Cart not found for userId: " + userId + " isbn: " + isbn
                        )
                );

        repo.delete(cart);

        return "Cart Deleted Successfully for userId: " + userId + " isbn: " + isbn;
    }

    // ✅ CHECKOUT (basic)
    public String checkout(Integer userId) {

        List<ShoppingCart> carts = repo.findByIdUserId(userId);

        if (carts.isEmpty()) {
            throw new EmptyCartException("Cannot checkout. Cart is empty for userId: " + userId);
        }

        return "Checkout successful for userId: " + userId;
    }

    // ✅ MAPPER
    private ShoppingCartResponseDTO mapToDTO(ShoppingCart entity) {

        ShoppingCartResponseDTO dto = new ShoppingCartResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setIsbn(entity.getIsbn());

        return dto;
    }
}