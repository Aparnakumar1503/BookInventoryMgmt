package com.sprint.BookInventoryMgmt.reviewmgmt.controller;

import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.ReviewerRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.ReviewerResponseDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.service.ReviewerService;
import com.sprint.BookInventoryMgmt.reviewmgmt.util.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    @Autowired
    private ReviewerService service;

    @PostMapping("/add")
    public ResponseStructure<ReviewerResponseDTO> addReviewer(
            @RequestBody ReviewerRequestDTO dto) {

        return new ResponseStructure<>(
                201,
                "Reviewer added successfully",
                service.addReviewer(dto)
        );
    }

    @GetMapping("/{id}")
    public ResponseStructure<ReviewerResponseDTO> getReviewerById(
            @PathVariable int id) {

        return new ResponseStructure<>(
                200,
                "Reviewer fetched successfully",
                service.getReviewerById(id)
        );
    }

    @GetMapping
    public ResponseStructure<List<ReviewerResponseDTO>> getAllReviewers() {

        return new ResponseStructure<>(
                200,
                "All reviewers fetched successfully",
                service.getAllReviewers()
        );
    }

    @PutMapping("/{id}")
    public ResponseStructure<ReviewerResponseDTO> updateReviewer(
            @PathVariable int id,
            @RequestBody ReviewerRequestDTO dto) {

        return new ResponseStructure<>(
                200,
                "Reviewer updated successfully",
                service.updateReviewer(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseStructure<String> deleteReviewer(@PathVariable int id) {

        service.deleteReviewer(id);

        return new ResponseStructure<>(
                200,
                "Reviewer deleted successfully",
                "Deleted ID: " + id
        );
    }
}