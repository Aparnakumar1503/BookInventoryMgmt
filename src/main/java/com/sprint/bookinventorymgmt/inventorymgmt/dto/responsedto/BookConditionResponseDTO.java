package com.sprint.BookInventoryMgmt.inventorymgmt.dto.responsedto;

public class BookConditionResponseDTO {

    private Integer ranks;
    private String description;
    private String fullDescription;
    private Double price;

    public BookConditionResponseDTO() {}

    public BookConditionResponseDTO(Integer ranks, String description, String fullDescription, Double price) {
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