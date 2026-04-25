package com.sprint.bookinventorymgmt.ordermgmt.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// Marks this class as an embeddable composite key used inside ShoppingCart entity
@Embeddable

// Serializable is required for composite key classes in JPA for proper persistence handling
public class ShoppingCartId implements Serializable {

    // Part of composite primary key representing user ID
    private Integer userId;

    // Part of composite primary key representing book ISBN
    private String isbn;

    // Default constructor required by JPA for object creation and reflection
    public ShoppingCartId() {}

    // Parameterized constructor to initialize composite key values
    public ShoppingCartId(Integer userId, String isbn) {
        this.userId = userId;
        this.isbn = isbn;
    }

    // Getter method to access userId from composite key
    public Integer getUserId() {
        return userId;
    }

    // Setter method to assign userId in composite key
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getter method to access ISBN from composite key
    public String getIsbn() {
        return isbn;
    }

    // Setter method to assign ISBN in composite key
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Overrides equals method to compare composite key objects logically
    @Override
    public boolean equals(Object o) {
        // Checks if both objects refer to same memory location
        if (this == o) return true;

        // Ensures object type matches before comparison
        if (!(o instanceof ShoppingCartId)) return false;

        // Typecast for field comparison
        ShoppingCartId that = (ShoppingCartId) o;

        // Compares both fields for equality
        return Objects.equals(userId, that.userId) &&
                Objects.equals(isbn, that.isbn);
    }

    // Generates consistent hash code based on composite key fields
    @Override
    public int hashCode() {
        return Objects.hash(userId, isbn);
    }
}