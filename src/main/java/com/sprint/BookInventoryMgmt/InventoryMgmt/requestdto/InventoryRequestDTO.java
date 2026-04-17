package com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto;

public class InventoryRequestDTO {

    private String isbn;
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