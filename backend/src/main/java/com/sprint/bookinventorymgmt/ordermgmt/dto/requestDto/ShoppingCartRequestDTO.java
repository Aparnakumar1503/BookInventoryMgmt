package com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ShoppingCartRequestDTO {

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be greater than 0")
    private Integer userId;

    @NotBlank(message = "ISBN cannot be blank")
    @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters")
    private String isbn;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
