package com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto;

import jakarta.validation.constraints.*;

public class BookConditionRequestDTO {

    @NotNull(message = "Rank is required")
    @Positive(message = "Rank must be greater than 0")
    private Integer ranks;

    @NotBlank(message = "Description is required")
    @Size(max = 100, message = "Description cannot exceed 100 characters")
    private String description;

    @Size(max = 255, message = "Full description cannot exceed 255 characters")
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
