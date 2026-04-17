package com.sprint.BookInventoryMgmt.reviewmgmt.controller;

import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.ReviewerRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.ReviewerResponseDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.service.ReviewerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    @Autowired
    private ReviewerService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ReviewerResponseDTO addReviewer(@Valid @RequestBody ReviewerRequestDTO dto) {
        return service.addReviewer(dto);
    }

    @GetMapping("/{id}")
    public ReviewerResponseDTO getReviewerById(@PathVariable int id) {
        return service.getReviewerById(id);
    }

    @GetMapping
    public List<ReviewerResponseDTO> getAllReviewers() {
        return service.getAllReviewers();
    }

    @PutMapping("/{id}")
    public ReviewerResponseDTO updateReviewer(
            @PathVariable int id,
            @Valid @RequestBody ReviewerRequestDTO dto) {
        return service.updateReviewer(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReviewer(@PathVariable int id) {
        service.deleteReviewer(id);
        return ResponseEntity.ok("Review deleted successfully with ID: " + id);
    }
}