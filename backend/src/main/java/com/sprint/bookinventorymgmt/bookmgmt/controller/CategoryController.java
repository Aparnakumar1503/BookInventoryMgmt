package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.response.CategoryResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.ICategoryService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category APIs", description = "CRUD operations for Categories")
@Validated
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseStructure<PaginatedResponse<CategoryResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {

        return ResponseBuilder.success(
                200,
                "Categories fetched successfully",
                PaginationUtils.paginate(categoryService.getAllCategories(), page, size)
        );
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{categoryId}")
    public ResponseStructure<CategoryResponseDTO> getById(
            @PathVariable @Positive(message = "Category ID must be greater than 0") Integer categoryId) {

        return ResponseBuilder.success(
                200,
                "Category fetched successfully",
                categoryService.getCategoryById(categoryId)
        );
    }
}
