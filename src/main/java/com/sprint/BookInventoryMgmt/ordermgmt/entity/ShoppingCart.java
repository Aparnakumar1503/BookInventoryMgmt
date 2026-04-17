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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;


}