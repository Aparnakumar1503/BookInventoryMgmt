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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Service implementation for ShoppingCart module
// Handles cart operations like add, fetch, delete, and filtering purchased items
@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    // Repository for cart database operations
    private final IShoppingCartRepository repo;

    // Repository to check purchased items from order history
    private final IPurchaseLogRepository purchaseLogRepository;

    // Repository to fetch inventory details for validation
    private final IInventoryRepository inventoryRepository;

    // Repository to verify book existence before adding to cart
    private final BookRepository bookRepository;

    // Constructor-based dependency injection for better testability and clean architecture
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

    // Adds a book to shopping cart after validating business rules
    @Override
    public ShoppingCartResponseDTO addCart(ShoppingCartRequestDTO dto) {

        // Validates input request to ensure required fields are present
        if (dto == null || dto.getUserId() == null || dto.getIsbn() == null || dto.getIsbn().isBlank()) {
            throw new EmptyCartException("UserId and isbn required");
        }

        // Checks if same item already exists in cart to avoid duplicates
        ShoppingCart existing = repo.findByUserIdAndIsbn(dto.getUserId(), dto.getIsbn());
        if (existing != null) {
            throw new DuplicateCartItemException("Item already exists");
        }

        // Validates whether the book exists in book database before adding to cart
        if (!bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new BookNotAvailableException(dto.getIsbn(), true);
        }

        // Creates cart entity using composite key (userId + isbn)
        ShoppingCart entity = new ShoppingCart(dto.getUserId(), dto.getIsbn());

        // Saves cart item into database
        ShoppingCart saved = repo.save(entity);

        // Converts entity to response DTO
        return mapToDTO(saved);
    }

    // Fetches all cart items (with transactional safety for cleanup logic)
    @Override
    @Transactional
    public List<ShoppingCartResponseDTO> getAll() {

        List<ShoppingCart> list = repo.findAll();

        // Removes items that are already purchased
        list = removePurchasedItems(list);

        List<ShoppingCartResponseDTO> response = new ArrayList<>();

        // Converts entity list to DTO list
        for (ShoppingCart c : list) {
            response.add(mapToDTO(c));
        }

        return response;
    }

    // Fetches cart items for a specific user
    @Override
    @Transactional
    public List<ShoppingCartResponseDTO> getByUserId(Integer userId) {

        List<ShoppingCart> list = repo.findByIdUserId(userId);

        // Removes already purchased items for clean cart view
        list = removePurchasedItems(list);

        List<ShoppingCartResponseDTO> response = new ArrayList<>();

        // Converts entity list to DTO list
        for (ShoppingCart c : list) {
            response.add(mapToDTO(c));
        }

        return response;
    }

    // Deletes a specific cart item using composite key (userId + isbn)
    @Override
    public String delete(Integer userId, String isbn) {

        ShoppingCartId id = new ShoppingCartId(userId, isbn);

        // Fetches cart item or throws exception if not found
        ShoppingCart cart = repo.findById(id)
                .orElseThrow(() ->
                        new ShoppingCartNotFoundException("Cart not found"));

        // Deletes cart item from database
        repo.delete(cart);

        return "Cart Deleted Successfully for userId: " + userId + " isbn: " + isbn;
    }

    // Converts ShoppingCart entity to ShoppingCartResponseDTO
    private ShoppingCartResponseDTO mapToDTO(ShoppingCart entity) {

        ShoppingCartResponseDTO dto = new ShoppingCartResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setIsbn(entity.getIsbn());

        return dto;
    }

    // Removes items from cart if they are already purchased
    // WHY: Ensures user does not see already purchased books in cart
    private List<ShoppingCart> removePurchasedItems(List<ShoppingCart> cartItems) {

        List<ShoppingCart> visibleItems = new ArrayList<>();

        for (ShoppingCart cartItem : cartItems) {

            // If already purchased, remove it from cart
            if (hasPurchasedBook(cartItem.getUserId(), cartItem.getIsbn())) {
                repo.delete(cartItem);
                continue;
            }

            visibleItems.add(cartItem);
        }

        // Flush ensures DB is updated immediately
        repo.flush();

        return visibleItems;
    }

    // Checks whether a user has already purchased a book
    private boolean hasPurchasedBook(Integer userId, String isbn) {

        return purchaseLogRepository.findByIdUserId(userId).stream()

                // Converts purchase logs into inventory entries
                .map(purchase -> inventoryRepository.findById(purchase.getInventoryId()).orElse(null))

                // Removes null inventory records
                .filter(inventory -> inventory != null)

                // Extracts ISBN from inventory
                .map(Inventory::getIsbn)

                // Collects ISBNs into a set for fast lookup
                .collect(Collectors.toSet())

                // Checks if current ISBN exists in purchased list
                .contains(isbn);
    }
}

