package com.sprint.bookinventorymgmt.ordermgmt.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseLogId implements Serializable {

    private Integer userId;
    private Integer inventoryId;

    public PurchaseLogId() {}

    public PurchaseLogId(Integer userId, Integer inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getInventoryId() { return inventoryId; }
    public void setInventoryId(Integer inventoryId) { this.inventoryId = inventoryId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseLogId)) return false;
        PurchaseLogId that = (PurchaseLogId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(inventoryId, that.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, inventoryId);
    }
}