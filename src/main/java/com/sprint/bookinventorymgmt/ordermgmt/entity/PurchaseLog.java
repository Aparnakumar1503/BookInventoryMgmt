package com.sprint.bookinventorymgmt.ordermgmt.entity;

import jakarta.persistence.*;

// Marks this class as a JPA entity mapped to a database table
@Entity

// Specifies the table name in the database as "purchaselog"
@Table(name = "purchaselog")
public class PurchaseLog {

    // Represents a composite primary key using an embedded ID class
    @EmbeddedId
    private PurchaseLogId id;

    // Default constructor required by JPA for entity creation
    public PurchaseLog() {}

    // Parameterized constructor to create PurchaseLog using userId and inventoryId
    public PurchaseLog(Integer userId, Integer inventoryId) {
        this.id = new PurchaseLogId(userId, inventoryId);
    }

    // Getter method to retrieve the composite primary key object
    public PurchaseLogId getId() {
        return id;
    }

    // Setter method to assign composite key value
    public void setId(PurchaseLogId id) {
        this.id = id;
    }

    // Convenience method to directly fetch userId from composite key
    public Integer getUserId() {
        return id.getUserId();
    }

    // Convenience method to directly fetch inventoryId from composite key
    public Integer getInventoryId() {
        return id.getInventoryId();
    }
}