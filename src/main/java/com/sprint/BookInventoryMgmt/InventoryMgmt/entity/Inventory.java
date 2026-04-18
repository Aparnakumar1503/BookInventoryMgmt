package com.sprint.BookInventoryMgmt.InventoryMgmt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "ranks", nullable = false)
    private Integer ranks;

    @Column(name = "purchased")
    private Boolean purchased = false;

    public Inventory() {}

    public Inventory(Integer inventoryId, String isbn, Integer ranks, Boolean purchased) {
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