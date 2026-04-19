package com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseLogRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotNull(message = "Inventory ID cannot be null")
    private Integer inventoryId;
}