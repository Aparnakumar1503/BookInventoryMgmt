package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;

import java.util.List;


// Service layer interface for PurchaseLog module
// WHY: Defines business operations separately from controller and repository for clean architecture
public interface IPurchaseLogService {

    // Business method to add a new purchase record
    // WHY: Handles validation, business rules, and mapping between DTO and entity
    PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO requestDTO);

    // Business method to fetch all purchase records
    // WHY: Used for admin or reporting purposes to view all transactions
    List<PurchaseLogResponseDTO> getAll();

    // Business method to fetch purchases of a specific user
    // WHY: Used to show purchase history for a particular user
    List<PurchaseLogResponseDTO> getByUserId(Integer userId);

    // Business method to delete a purchase record using composite key
    // WHY: Ensures controlled deletion with business validation instead of direct DB access
    String delete(Integer userId, Integer inventoryId);
}