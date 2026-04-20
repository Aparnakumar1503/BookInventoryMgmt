package com.sprint.BookInventoryMgmt.bookmgmt.service;

import com.sprint.BookInventoryMgmt.bookmgmt.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Integer id);
}