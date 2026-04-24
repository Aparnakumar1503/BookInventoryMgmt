package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.response.CategoryResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.ICategoryService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles read-only category endpoints so reference data stays separate from book CRUD operations.
 */
@RestController
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns all categories used by the catalog.
     */
    @GetMapping("/api/v1/categories")
    public ResponseStructure<PaginatedResponse<CategoryResponseDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Categories fetched successfully",
                PaginationUtils.paginate(categoryService.getAllCategories(), page, size)
        );
    }

    /**
     * Returns one category by identifier.
     */
    @GetMapping("/api/v1/categories/{categoryId}")
    public ResponseStructure<CategoryResponseDTO> getCategoryById(@PathVariable Integer categoryId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Category fetched successfully",
                categoryService.getCategoryById(categoryId)
        );
    }
}
