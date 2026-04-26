package com.sprint.bookinventorymgmt.reviewmgmt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class ReviewerRequestDTO {

    @NotBlank(message = "Reviewer name is required")
    @Size(min = 2, max = 100, message = "Reviewer name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Employer name is required")
    @Size(min = 2, max = 100, message = "Employer name must be between 2 and 100 characters")
    private String employedBy;
    public ReviewerRequestDTO() {
    }

    public ReviewerRequestDTO(String name, String employedBy) {
        this.name = name;
        this.employedBy = employedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployedBy() {
        return employedBy;
    }

    public void setEmployedBy(String employedBy) {
        this.employedBy = employedBy;
    }

}
