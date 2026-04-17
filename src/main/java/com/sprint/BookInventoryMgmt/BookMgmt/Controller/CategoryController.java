package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Category;
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
    public List<Category> getAll() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }
}