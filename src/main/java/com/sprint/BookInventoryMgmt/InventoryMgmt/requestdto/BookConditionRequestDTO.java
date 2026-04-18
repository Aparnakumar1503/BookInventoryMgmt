package com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto;

import jakarta.validation.constraints.*;

public class BookConditionRequestDTO {

    @NotNull(message = "Rank is required")
    private Integer ranks;

    @NotBlank(message = "Description is required")
    private String description;

    private String fullDescription;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    public BookConditionRequestDTO() {}

    public BookConditionRequestDTO(Integer ranks, String description, String fullDescription, Double price) {
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