package com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShoppingCartRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;
}