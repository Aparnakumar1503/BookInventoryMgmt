package com.sprint.bookinventorymgmt.bookmgmt.dto.response;

public class CategoryResponseDTO {

    private Integer catId;
    private String catDescription;

    public CategoryResponseDTO() {}

    public CategoryResponseDTO(Integer catId, String catDescription) {
        this.catId = catId;
        this.catDescription = catDescription;
    }

    public Integer getCatId() { return catId; }
    public void setCatId(Integer catId) { this.catId = catId; }

    public String getCatDescription() { return catDescription; }
    public void setCatDescription(String catDescription) { this.catDescription = catDescription; }
}