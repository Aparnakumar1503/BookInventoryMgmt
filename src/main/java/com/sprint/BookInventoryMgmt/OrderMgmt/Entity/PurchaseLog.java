package com.sprint.BookInventoryMgmt.OrderMgmt.Entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_log")
public class PurchaseLog {

    @Id
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Inventory ID cannot be null")
    private Long inventoryId;


}