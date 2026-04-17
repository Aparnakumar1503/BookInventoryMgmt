package com.sprint.BookInventoryMgmt.orderMgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;

    public ShoppingCart() {
    }
    public ShoppingCart(Long userId, String isbn) {
        this.userId = userId;
        this.isbn = isbn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}