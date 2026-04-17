package com.sprint.BookInventoryMgmt.orderMgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "purchase_log")
public class PurchaseLog {

    @Id
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Inventory ID cannot be null")
    private Long inventoryId;

    public PurchaseLog() {
    }
    public PurchaseLog(Long userId, Long inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }
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