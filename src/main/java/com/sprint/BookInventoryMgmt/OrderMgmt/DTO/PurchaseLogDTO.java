package com.sprint.BookInventoryMgmt.OrderMgmt.DTO;


import jakarta.validation.constraints.NotNull;

public class PurchaseLogDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Inventory ID cannot be null")
    private Long inventoryId;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }
}