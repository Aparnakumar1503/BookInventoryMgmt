package com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PurchaseLogRequestDTO {

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be greater than 0")
    private Integer userId;

    @NotNull(message = "Inventory ID cannot be null")
    @Positive(message = "Inventory ID must be greater than 0")
    private Integer inventoryId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }
}
