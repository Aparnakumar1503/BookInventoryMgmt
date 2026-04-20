package com.sprint.BookInventoryMgmt.ordermgmt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "purchaselog")
public class PurchaseLog {

    @EmbeddedId
    private PurchaseLogId id;

    public PurchaseLog() {}

    public PurchaseLog(Integer userId, Integer inventoryId) {
        this.id = new PurchaseLogId(userId, inventoryId);
    }

    public PurchaseLogId getId() { return id; }
    public void setId(PurchaseLogId id) { this.id = id; }

    // Convenience getters
    public Integer getUserId() { return id.getUserId(); }
    public Integer getInventoryId() { return id.getInventoryId(); }
}