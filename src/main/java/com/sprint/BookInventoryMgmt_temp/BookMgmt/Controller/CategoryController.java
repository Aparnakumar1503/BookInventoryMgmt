package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

import com.sprint.BookInventoryMgmt.BookMgmt.DTO.response.CategoryResponseDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.Service.CategoryService;
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