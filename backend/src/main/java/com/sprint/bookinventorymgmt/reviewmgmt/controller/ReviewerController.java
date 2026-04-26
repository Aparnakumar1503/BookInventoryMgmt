package com.sprint.bookinventorymgmt.reviewmgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.service.ReviewerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviewers")
@Validated
public class ReviewerController {
    @Autowired
    private ReviewerService service;

    public ReviewerController(ReviewerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseStructure<ReviewerResponseDTO> addReviewer(
            @Valid @RequestBody ReviewerRequestDTO dto) {
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Reviewer added successfully",
                service.addReviewer(dto)
        );
    }

    @GetMapping("/{id}")
    public ResponseStructure<ReviewerResponseDTO> getReviewerById(
            @PathVariable @Positive(message = "Reviewer ID must be greater than 0") int id) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviewer fetched successfully",
                service.getReviewerById(id)
        );
    }

    @GetMapping
    public ResponseStructure<PaginatedResponse<ReviewerResponseDTO>> getAllReviewers(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "All reviewers fetched successfully",
                PaginationUtils.paginate(service.getAllReviewers(), page, size)
        );
    }

    @GetMapping("/company/{company}")
    public ResponseStructure<PaginatedResponse<ReviewerResponseDTO>> getReviewersByCompany(
            @PathVariable @NotBlank(message = "Company cannot be blank") @Size(max = 100, message = "Company cannot exceed 100 characters") String company,
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviewers fetched successfully by company",
                PaginationUtils.paginate(service.getReviewersByCompany(company), page, size)
        );
    }

    @PutMapping("/{id}")
    public ResponseStructure<ReviewerResponseDTO> updateReviewer(
            @PathVariable @Positive(message = "Reviewer ID must be greater than 0") int id,
            @Valid @RequestBody ReviewerRequestDTO dto) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviewer updated successfully",
                service.updateReviewer(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseStructure<String> deleteReviewer(
            @PathVariable @Positive(message = "Reviewer ID must be greater than 0") int id) {
        service.deleteReviewer(id);
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviewer deleted successfully",
                "Deleted ID: " + id
        );
    }
}
