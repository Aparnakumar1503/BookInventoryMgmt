package com.sprint.BookInventoryMgmt.InventoryMgmt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bookcondition")
public class BookCondition{

    @Id
    @Column(name = "ranks")
    private Integer ranks;

    @Column(name = "description")
    private String description;

    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "price")
    private Double price;

    public BookCondition() {}

    public BookCondition(Integer ranks, String description, String fullDescription, Double price) {
        this.ranks = ranks;
        this.description = description;
        this.fullDescription = fullDescription;
        this.price = price;
    }

    public Integer getRanks() { return ranks; }
    public void setRanks(Integer ranks) { this.ranks = ranks; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFullDescription() { return fullDescription; }
    public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}