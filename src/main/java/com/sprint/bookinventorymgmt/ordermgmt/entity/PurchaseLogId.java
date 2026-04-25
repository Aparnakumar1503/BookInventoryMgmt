package com.sprint.bookinventorymgmt.ordermgmt.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// Marks this class as an embeddable composite key used inside another entity
@Embeddable

// Serializable is required for composite key classes in JPA to ensure object can be serialized
public class PurchaseLogId implements Serializable {

    // Part of composite primary key representing user ID
    private Integer userId;

    // Part of composite primary key representing inventory/book ID
    private Integer inventoryId;

    // Default constructor required by JPA for object creation and reflection
    public PurchaseLogId() {}

    // Parameterized constructor to initialize composite key values
    public PurchaseLogId(Integer userId, Integer inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }

    // Getter method to access userId from composite key
    public Integer getUserId() {
        return userId;
    }

    // Setter method to assign userId in composite key
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getter method to access inventoryId from composite key
    public Integer getInventoryId() {
        return inventoryId;
    }

    // Setter method to assign inventoryId in composite key
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    // Overrides equals method to compare composite key objects logically
    @Override
    public boolean equals(Object o) {
        // Checks if both references point to same object
        if (this == o) return true;

        // Ensures object is of same type before comparison
        if (!(o instanceof PurchaseLogId)) return false;

        // Typecast for field comparison
        PurchaseLogId that = (PurchaseLogId) o;

        // Compares both fields for equality
        return Objects.equals(userId, that.userId) &&
                Objects.equals(inventoryId, that.inventoryId);
    }

    // Generates consistent hash code based on composite key fields
    @Override
    public int hashCode() {
        return Objects.hash(userId, inventoryId);
    }
}