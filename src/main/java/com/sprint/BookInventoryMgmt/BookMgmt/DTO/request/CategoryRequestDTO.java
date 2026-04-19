package com.sprint.BookInventoryMgmt.bookmgmt.dto.request;

import jakarta.validation.constraints.NotNull;

public class CategoryRequestDTO {

    @NotNull
    private Integer catId;

    public Integer getCatId() { return catId; }
    public void setCatId(Integer catId) { this.catId = catId; }
}