package com.sprint.BookInventoryMgmt.inventorymgmt.dto.requestdto;

import jakarta.validation.constraints.*;

public class InventoryRequestDTO {

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Rank is required")
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