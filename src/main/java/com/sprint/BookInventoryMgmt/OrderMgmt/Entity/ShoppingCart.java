package com.sprint.BookInventoryMgmt.OrderMgmt.Entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @NotNull(message = "User ID cannot be null")
    private Long userId;   // PK (as per your requirement)

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;   // Second identifier (no composite logic)


}