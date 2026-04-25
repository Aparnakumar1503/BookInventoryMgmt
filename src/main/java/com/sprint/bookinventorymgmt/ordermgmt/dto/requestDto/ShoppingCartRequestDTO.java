package com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// DTO class used to receive shopping cart data from client to backend
public class ShoppingCartRequestDTO {

    // Ensures userId is not null and must be a valid positive number
    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be greater than 0")
    private Integer userId;

    // Ensures ISBN is not empty or just whitespace
    // Also restricts length to avoid invalid or malformed ISBN values
    @NotBlank(message = "ISBN cannot be blank")
    @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters")
    private String isbn;

    // Getter method to retrieve userId from request object
    public Integer getUserId() {
        return userId;
    }

    // Setter method to assign userId value from incoming request
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getter method to retrieve ISBN value
    public String getIsbn() {
        return isbn;
    }

    // Setter method to assign ISBN value from incoming request
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}