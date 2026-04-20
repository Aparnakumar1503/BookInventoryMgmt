package com.sprint.bookinventorymgmt.bookmgmt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryRequestDTO {

    @NotNull(message = "Category ID is required")
    private Integer catId;

    @NotBlank(message = "Category description is required")
    @Size(min = 2, max = 100, message = "Category description must be between 2 and 100 characters")
    private String catDescription;

    public CategoryRequestDTO() {}

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }
}