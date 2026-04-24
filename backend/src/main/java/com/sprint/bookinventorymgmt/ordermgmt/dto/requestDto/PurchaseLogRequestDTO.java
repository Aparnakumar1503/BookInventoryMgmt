package com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto;

import jakarta.validation.constraints.NotNull;

public class PurchaseLogRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotNull(message = "Inventory ID cannot be null")
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
