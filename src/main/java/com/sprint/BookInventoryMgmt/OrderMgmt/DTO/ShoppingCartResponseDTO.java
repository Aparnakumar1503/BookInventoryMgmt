package com.sprint.BookInventoryMgmt.OrderMgmt.DTO;


import lombok.Data;

@Data
public class ShoppingCartResponseDTO {

    private Long userId;
    private String isbn;
}