package com.sprint.bookinventorymgmt.reviewmgmt.dto;

import jakarta.validation.constraints.NotBlank;



public class ReviewerRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
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