package com.sprint.BookInventoryMgmt.OrderMgmt.dto.requestdto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseLogRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Inventory ID cannot be null")
    private Long inventoryId;
}