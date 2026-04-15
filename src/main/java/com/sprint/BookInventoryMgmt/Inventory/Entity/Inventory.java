package com.sprint.BookInventoryMgmt.Inventory.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @NotBlank(message = "ISBN cannot be empty")
    @Column(name = "isbn", nullable = false, length = 13)
    private String isbn;

    @NotNull(message = "Rank is required")
    @Column(name = "ranks", nullable = false)
    private Integer ranks;

    @Column(name = "purchased")
    private Boolean purchased = false;

}
