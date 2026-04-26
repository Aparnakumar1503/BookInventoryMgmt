package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.bookmgmt.repository.BookRepository;
import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import com.sprint.bookinventorymgmt.inventorymgmt.repository.IInventoryRepository;
import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCartId;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.*;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IPurchaseLogRepository;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IShoppingCartRepository;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Autowired
    private  IShoppingCartRepository repo;
    @Autowired
    private  IPurchaseLogRepository purchaseLogRepository;
    @Autowired
    private  IInventoryRepository inventoryRepository;
    @Autowired
    private BookRepository bookRepository;

    public ShoppingCartServiceImpl(
            IShoppingCartRepository repo,
            IPurchaseLogRepository purchaseLogRepository,
            IInventoryRepository inventoryRepository,
            BookRepository bookRepository) {
        this.repo = repo;
        this.purchaseLogRepository = purchaseLogRepository;
        this.inventoryRepository = inventoryRepository;
        this.bookRepository = bookRepository;
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

        if (!bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new BookNotAvailableException(dto.getIsbn(), true);
        }

        ShoppingCart entity = new ShoppingCart(dto.getUserId(), dto.getIsbn());

        ShoppingCart saved = repo.save(entity);

        return mapToDTO(saved);
    }

    @Override
    @Transactional
    public List<ShoppingCartResponseDTO> getAll() {

        List<ShoppingCart> list = repo.findAll();
        list = removePurchasedItems(list);
        List<ShoppingCartResponseDTO> response = new ArrayList<>();

        for (ShoppingCart c : list) {
            response.add(mapToDTO(c));
        }

        return response;
    }

    @Override
    @Transactional
    public List<ShoppingCartResponseDTO> getByUserId(Integer userId) {

        List<ShoppingCart> list = repo.findByIdUserId(userId);
        list = removePurchasedItems(list);
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

    private List<ShoppingCart> removePurchasedItems(List<ShoppingCart> cartItems) {
        List<ShoppingCart> visibleItems = new ArrayList<>();

        for (ShoppingCart cartItem : cartItems) {
            if (hasPurchasedBook(cartItem.getUserId(), cartItem.getIsbn())) {
                repo.delete(cartItem);
                continue;
            }
            visibleItems.add(cartItem);
        }

        repo.flush();
        return visibleItems;
    }

    private boolean hasPurchasedBook(Integer userId, String isbn) {
        return purchaseLogRepository.findByIdUserId(userId).stream()
                .map(purchase -> inventoryRepository.findById(purchase.getInventoryId()).orElse(null))
                .filter(inventory -> inventory != null)
                .map(Inventory::getIsbn)
                .collect(Collectors.toSet())
                .contains(isbn);
    }
}
