package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.DTO.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Integer id);
}