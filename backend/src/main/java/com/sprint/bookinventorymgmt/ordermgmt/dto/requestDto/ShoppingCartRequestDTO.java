package com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class ShoppingCartRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotBlank(message = "ISBN cannot be blank")
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
