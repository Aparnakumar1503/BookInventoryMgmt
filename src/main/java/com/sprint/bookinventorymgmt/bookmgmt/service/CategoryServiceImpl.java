package com.sprint.BookInventoryMgmt.bookmgmt.service;

import com.sprint.BookInventoryMgmt.bookmgmt.dto.response.CategoryResponseDTO;
import com.sprint.BookInventoryMgmt.bookmgmt.entity.Category;
import com.sprint.BookInventoryMgmt.bookmgmt.exceptions.CategoryNotFoundException;
import com.sprint.BookInventoryMgmt.bookmgmt.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return mapToDTO(category);
    }

    private CategoryResponseDTO mapToDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setCatId(category.getCatId());
        dto.setCatDescription(category.getCatDescription());
        return dto;
    }
}