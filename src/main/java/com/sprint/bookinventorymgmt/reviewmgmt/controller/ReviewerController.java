package com.sprint.bookinventorymgmt.reviewmgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.service.ReviewerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviewers")
public class ReviewerController {

    @Autowired
    private ReviewerService service;

    @PostMapping
    public ResponseStructure<ReviewerResponseDTO> addReviewer(
            @Valid @RequestBody ReviewerRequestDTO dto) {

        return new ResponseStructure<>(
                HttpStatus.CREATED.value(),
                "Reviewer added successfully",
                service.addReviewer(dto)
        );
    }

    @GetMapping("/{id}")
    public ResponseStructure<ReviewerResponseDTO> getReviewerById(
            @PathVariable int id) {

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Reviewer fetched successfully",
                service.getReviewerById(id)
        );
    }

    @GetMapping
    public ResponseStructure<List<ReviewerResponseDTO>> getAllReviewers() {

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "All reviewers fetched successfully",
                service.getAllReviewers()
        );
    }

    @PutMapping("/{id}")
    public ResponseStructure<ReviewerResponseDTO> updateReviewer(
            @PathVariable int id,
            @Valid @RequestBody ReviewerRequestDTO dto) {

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Reviewer updated successfully",
                service.updateReviewer(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseStructure<String> deleteReviewer(@PathVariable int id) {

        service.deleteReviewer(id);

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Reviewer deleted successfully",
                "Deleted ID: " + id
        );
    }
}
