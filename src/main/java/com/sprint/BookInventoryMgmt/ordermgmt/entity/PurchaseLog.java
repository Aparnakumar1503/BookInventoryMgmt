package com.sprint.BookInventoryMgmt.ordermgmt.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_log")

public class PurchaseLog {

    @Id
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Inventory ID cannot be null")
    private Long inventoryId;


}