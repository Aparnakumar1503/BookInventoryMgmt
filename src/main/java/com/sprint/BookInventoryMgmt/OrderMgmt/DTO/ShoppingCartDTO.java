package com.sprint.BookInventoryMgmt.OrderMgmt.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShoppingCartDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;

    // Getters and Setters
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