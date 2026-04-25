package com.sprint.bookinventorymgmt.ordermgmt.entity;

import jakarta.persistence.*;

// Marks this class as a JPA entity mapped to a database table
@Entity

// Specifies the table name in the database as "shoppingcart"
@Table(name = "shoppingcart")
public class ShoppingCart {

    // Represents a composite primary key using an embedded ID class
    @EmbeddedId
    private ShoppingCartId id;

    // Default constructor required by JPA for entity creation and reflection
    public ShoppingCart() {}

    // Parameterized constructor to create ShoppingCart using userId and isbn
    public ShoppingCart(Integer userId, String isbn) {
        this.id = new ShoppingCartId(userId, isbn);
    }

    // Getter method to retrieve the composite primary key object
    public ShoppingCartId getId() {
        return id;
    }

    // Setter method to assign composite key value
    public void setId(ShoppingCartId id) {
        this.id = id;
    }

    // Convenience method to directly fetch userId from composite key
    public Integer getUserId() {
        return id.getUserId();
    }

    // Convenience method to directly fetch ISBN from composite key
    public String getIsbn() {
        return id.getIsbn();
    }
}