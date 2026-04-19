package com.sprint.BookInventoryMgmt.inventorymgmt.dto.responsedto;

public class InventoryResponseDTO {

    private Integer inventoryId;
    private String isbn;
    private Integer ranks;
    private Boolean purchased;

    public InventoryResponseDTO() {}

    public InventoryResponseDTO(Integer inventoryId, String isbn, Integer ranks, Boolean purchased) {
        this.inventoryId = inventoryId;
        this.isbn = isbn;
        this.ranks = ranks;
        this.purchased = purchased;
    }

    public Integer getInventoryId() { return inventoryId; }
    public void setInventoryId(Integer inventoryId) { this.inventoryId = inventoryId; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getRanks() { return ranks; }
    public void setRanks(Integer ranks) { this.ranks = ranks; }

    public Boolean getPurchased() { return purchased; }
    public void setPurchased(Boolean purchased) { this.purchased = purchased; }
}