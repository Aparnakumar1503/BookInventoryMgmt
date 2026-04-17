package com.sprint.BookInventoryMgmt.reviewmgmt.controller;

import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.BookReviewRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.BookReviewResponseDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.service.BookReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reviews")
public class BookReviewController {

    @Autowired
    private BookReviewService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public BookReviewResponseDTO addReview(@Valid @RequestBody BookReviewRequestDTO dto) {
        return service.addReview(dto);
    }

    @GetMapping
    public List<BookReviewResponseDTO> getAllReviews() {
        return service.getAllReviews();
    }

    @GetMapping("/isbn/{isbn}")
    public List<BookReviewResponseDTO> getByISBN(@PathVariable String isbn) {
        return service.getReviewsByISBN(isbn);
    }

    @GetMapping("/reviewer/{reviewerId}")
    public List<BookReviewResponseDTO> getByReviewer(@PathVariable int reviewerId) {
        return service.getReviewsByReviewer(reviewerId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable int id) {
        service.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully with ID: " + id);
    }
}