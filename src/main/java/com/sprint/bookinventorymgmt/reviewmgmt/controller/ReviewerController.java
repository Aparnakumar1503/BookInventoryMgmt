package com.sprint.bookinventorymgmt.reviewmgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.service.ReviewerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Manages reviewer endpoints because reviewers are a separate domain entity from reviews.
 */
@RestController
public class ReviewerController {

    private final ReviewerService reviewerService;

    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    /**
     * Creates a reviewer.
     */
    @PostMapping("/api/v1/reviewers")
    public ResponseStructure<ReviewerResponseDTO> addReviewer(@Valid @RequestBody ReviewerRequestDTO dto) {
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Reviewer added successfully",
                reviewerService.addReviewer(dto)
        );
    }

    /**
     * Returns one reviewer by identifier.
     */
    @GetMapping("/api/v1/reviewers/{id}")
    public ResponseStructure<ReviewerResponseDTO> getReviewerById(@PathVariable int id) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviewer fetched successfully",
                reviewerService.getReviewerById(id)
        );
    }

    /**
     * Returns all reviewers.
     */
    @GetMapping("/api/v1/reviewers")
    public ResponseStructure<PaginatedResponse<ReviewerResponseDTO>> getAllReviewers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "All reviewers fetched successfully",
                PaginationUtils.paginate(reviewerService.getAllReviewers(), page, size)
        );
    }

    /**
     * Returns reviewers filtered by company.
     */
    @GetMapping("/api/v1/reviewers/company/{company}")
    public ResponseStructure<PaginatedResponse<ReviewerResponseDTO>> getReviewersByCompany(
            @PathVariable String company,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviewers fetched successfully by company",
                PaginationUtils.paginate(reviewerService.getReviewersByCompany(company), page, size)
        );
    }

    /**
     * Updates one reviewer.
     */
    @PutMapping("/api/v1/reviewers/{id}")
    public ResponseStructure<ReviewerResponseDTO> updateReviewer(
            @PathVariable int id,
            @Valid @RequestBody ReviewerRequestDTO dto) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviewer updated successfully",
                reviewerService.updateReviewer(id, dto)
        );
    }

    /**
     * Deletes one reviewer by identifier.
     */
    @DeleteMapping("/api/v1/reviewers/{id}")
    public ResponseStructure<ReviewerResponseDTO> deleteReviewer(@PathVariable int id) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviewer deleted successfully",
                reviewerService.deleteReviewer(id)
        );
    }
}
