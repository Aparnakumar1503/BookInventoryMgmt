package com.sprint.BookInventoryMgmt.BookMgmt.DTO.requestdto;

import jakarta.validation.constraints.NotNull;

public class CategoryRequestDTO {

    @NotNull
    private Integer catId;

    public Integer getCatId() { return catId; }
    public void setCatId(Integer catId) { this.catId = catId; }
}