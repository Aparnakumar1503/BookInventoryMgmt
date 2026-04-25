package com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// DTO class used to capture purchase request data from client to backend
public class PurchaseLogRequestDTO {

    // Ensures userId is not null and must be a valid positive number
    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be greater than 0")
    private Integer userId;

    // Ensures inventoryId is not null and must be a valid positive number
    @NotNull(message = "Inventory ID cannot be null")
    @Positive(message = "Inventory ID must be greater than 0")
    private Integer inventoryId;

    // Getter method to retrieve userId value
    public Integer getUserId() {
        return userId;
    }

    // Setter method to assign userId value from incoming request
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getter method to retrieve inventoryId value
    public Integer getInventoryId() {
        return inventoryId;
    }

    // Setter method to assign inventoryId value from incoming request
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }
}
