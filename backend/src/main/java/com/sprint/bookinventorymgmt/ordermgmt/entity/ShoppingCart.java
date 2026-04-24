package com.sprint.bookinventorymgmt.ordermgmt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shoppingcart")
public class ShoppingCart {

    @EmbeddedId
    private ShoppingCartId id;

    public ShoppingCart() {}

    public ShoppingCart(Integer userId, String isbn) {
        this.id = new ShoppingCartId(userId, isbn);
    }

    public ShoppingCartId getId() { return id; }
    public void setId(ShoppingCartId id) { this.id = id; }

    // Convenience getters
    public Integer getUserId() { return id.getUserId(); }
    public String getIsbn() { return id.getIsbn(); }
}