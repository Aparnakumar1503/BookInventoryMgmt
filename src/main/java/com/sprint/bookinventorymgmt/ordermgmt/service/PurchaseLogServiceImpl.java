package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.DatabaseOperationException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InvalidInventoryDataException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InventoryNotFoundException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InventoryPurchaseException;
import com.sprint.bookinventorymgmt.inventorymgmt.repository.IInventoryRepository;
import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLog;
import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLogId;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.BookAlreadyPurchasedException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IPurchaseLogRepository;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IShoppingCartRepository;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

// Service implementation for PurchaseLog business logic
// Handles purchase creation, retrieval, deletion, and cart cleanup logic
@Service
public class PurchaseLogServiceImpl implements IPurchaseLogService {

    // Repository for PurchaseLog database operations
    private final IPurchaseLogRepository repo;

    // Repository to validate and update inventory data
    private final IInventoryRepository inventoryRepository;

    // Repository used to remove purchased items from shopping cart
    private final IShoppingCartRepository shoppingCartRepository;

    // Constructor-based dependency injection for better testability and loose coupling
    public PurchaseLogServiceImpl(
            IPurchaseLogRepository repo,
            IInventoryRepository inventoryRepository,
            IShoppingCartRepository shoppingCartRepository) {
        this.repo = repo;
        this.inventoryRepository = inventoryRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    // Adds a new purchase record and performs business validations
    @Override
    @Transactional
    public PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto) {

        // Validates input request to ensure required fields are not null
        if (dto == null || dto.getUserId() == null || dto.getInventoryId() == null) {
            throw new InvalidInventoryDataException("UserId and inventoryId are required");
        }

        // Checks if the same user already purchased the same inventory item
        PurchaseLog existing = repo.findByUserIdAndInventoryId(dto.getUserId(), dto.getInventoryId());
        if (existing != null) {
            throw new BookAlreadyPurchasedException(
                    "Already purchased for userId: " + dto.getUserId());
        }

        // Fetches inventory details to validate availability
        Inventory inventory = inventoryRepository.findById(dto.getInventoryId())
                .orElseThrow(() ->
                        new InventoryNotFoundException("Inventory not found with id: " + dto.getInventoryId()));

        // Ensures the inventory is not already marked as purchased
        if (Boolean.TRUE.equals(inventory.getPurchased())) {
            throw new InventoryPurchaseException("This inventory is already purchased");
        }

        // Creates PurchaseLog entity using composite key (userId + inventoryId)
        PurchaseLog entity = new PurchaseLog(dto.getUserId(), dto.getInventoryId());

        PurchaseLog saved;
        try {
            // Saves purchase record into database
            saved = repo.save(entity);

            // Marks inventory as purchased after successful order
            inventory.setPurchased(true);
            inventoryRepository.save(inventory);

            // Removes the purchased item from user's cart
            cleanupMatchingCartItem(dto.getUserId(), dto.getInventoryId());

        } catch (Exception e) {
            // Handles any unexpected database failures
            throw new DatabaseOperationException("Failed to create purchase", e);
        }

        // Converts entity to response DTO before returning to controller
        return mapToDTO(saved);
    }

    // Retrieves all purchase records from database
    @Override
    public List<PurchaseLogResponseDTO> getAll() {

        List<PurchaseLog> list = repo.findAll();
        List<PurchaseLogResponseDTO> response = new ArrayList<>();

        // Converts each entity to DTO
        for (PurchaseLog p : list) {
            response.add(mapToDTO(p));
        }

        return response;
    }

    // Retrieves purchase history for a specific user
    @Override
    public List<PurchaseLogResponseDTO> getByUserId(Integer userId) {

        List<PurchaseLog> list = repo.findByIdUserId(userId);
        List<PurchaseLogResponseDTO> response = new ArrayList<>();

        // Converts each entity to DTO
        for (PurchaseLog p : list) {
            response.add(mapToDTO(p));
        }

        return response;
    }

    // Deletes a purchase record using composite key (userId + inventoryId)
    @Override
    public String delete(Integer userId, Integer inventoryId) {

        PurchaseLogId id = new PurchaseLogId(userId, inventoryId);

        // Fetches purchase record or throws exception if not found
        PurchaseLog purchase = repo.findById(id)
                .orElseThrow(() ->
                        new PurchaseNotFoundException("Purchase not found"));

        // Deletes record from database
        repo.delete(purchase);

        return "Deleted Successfully for userId: " + userId + " inventoryId: " + inventoryId;
    }

    // Converts PurchaseLog entity to PurchaseLogResponseDTO
    private PurchaseLogResponseDTO mapToDTO(PurchaseLog entity) {

        PurchaseLogResponseDTO dto = new PurchaseLogResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setInventoryId(entity.getInventoryId());

        return dto;
    }

    // Removes item from shopping cart after successful purchase
    private void cleanupMatchingCartItem(Integer userId, Integer inventoryId) {

        inventoryRepository.findById(inventoryId).ifPresent(inventory -> {

            // Ensures ISBN exists before deleting from cart
            if (inventory.getIsbn() != null && !inventory.getIsbn().isBlank()) {

                // Deletes corresponding cart entry using userId + ISBN
                shoppingCartRepository.deleteByUserIdAndIsbn(userId, inventory.getIsbn());
            }
        });
    }
}