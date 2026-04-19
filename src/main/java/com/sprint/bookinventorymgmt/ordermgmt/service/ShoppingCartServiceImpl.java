package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCartId;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IShoppingCartRepository;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.ShoppingCartNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestdto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responsedto.ShoppingCartResponseDTO;
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
        // use composite key constructor
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