package com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto;

import jakarta.validation.constraints.*;

public class InventoryRequestDTO {

    @NotBlank(message = "ISBN is required")
    @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters")
    private String isbn;

    @NotNull(message = "Rank is required")
    @Positive(message = "Rank must be greater than 0")
    private Integer ranks;

    private Boolean purchased;

    public InventoryRequestDTO() {}

    public InventoryRequestDTO(String isbn, Integer ranks, Boolean purchased) {
        this.isbn = isbn;
        this.ranks = ranks;
        this.purchased = purchased;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getRanks() { return ranks; }
    public void setRanks(Integer ranks) { this.ranks = ranks; }

    public Boolean getPurchased() { return purchased; }
    public void setPurchased(Boolean purchased) { this.purchased = purchased; }
}
