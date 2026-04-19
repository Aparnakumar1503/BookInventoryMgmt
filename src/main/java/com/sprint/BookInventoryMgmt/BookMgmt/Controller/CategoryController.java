package com.sprint.BookInventoryMgmt.bookmgmt.controller;

import com.sprint.BookInventoryMgmt.bookmgmt.dto.response.CategoryResponseDTO;
import com.sprint.BookInventoryMgmt.bookmgmt.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }
}