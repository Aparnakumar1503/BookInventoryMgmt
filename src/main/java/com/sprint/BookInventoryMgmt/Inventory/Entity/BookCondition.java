package com.sprint.BookInventoryMgmt.Inventory.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "bookcondition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCondition {
    // Validation added and tested
    @Id
    @Column(name = "ranks")
    private Integer ranks;

    @NotBlank(message = "Description cannot be empty")
    @Column(name = "description")
    private String description;

    @Column(name = "full_description")
    private String fullDescription;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    @Column(name = "price")
    private Double price;
}
