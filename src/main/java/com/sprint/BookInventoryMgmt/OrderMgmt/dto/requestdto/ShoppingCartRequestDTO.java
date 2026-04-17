package com.sprint.BookInventoryMgmt.OrderMgmt.dto.requestdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShoppingCartRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;
}