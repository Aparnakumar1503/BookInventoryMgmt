package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.response.CategoryResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.ICategoryService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category APIs", description = "CRUD operations for Categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseStructure<PaginatedResponse<CategoryResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        return ResponseBuilder.success(
                200,
                "Categories fetched successfully",
                PaginationUtils.paginate(categoryService.getAllCategories(), page, size)
        );
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{categoryId}")
    public ResponseStructure<CategoryResponseDTO> getById(@PathVariable Integer categoryId) {

        return ResponseBuilder.success(
                200,
                "Category fetched successfully",
                categoryService.getCategoryById(categoryId)
        );
    }
}
