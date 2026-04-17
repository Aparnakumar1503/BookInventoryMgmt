package com.sprint.BookInventoryMgmt.reviewmgmt.controller;

import com.sprint.BookInventoryMgmt.common.ResponseStructure;
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

    @PostMapping("/add")
    public ResponseStructure<BookReviewResponseDTO> addReview(
            @Valid @RequestBody BookReviewRequestDTO dto) {

        return new ResponseStructure<>(
                HttpStatus.CREATED.value(),
                "Review added successfully",
                service.addReview(dto)
        );
    }

    @GetMapping
    public ResponseStructure<List<BookReviewResponseDTO>> getAllReviews() {

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "All reviews fetched successfully",
                service.getAllReviews()
        );
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseStructure<List<BookReviewResponseDTO>> getByISBN(
            @PathVariable String isbn) {

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Reviews fetched by ISBN",
                service.getReviewsByISBN(isbn)
        );
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseStructure<List<BookReviewResponseDTO>> getByReviewer(
            @PathVariable int reviewerId) {

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Reviews fetched by reviewer",
                service.getReviewsByReviewer(reviewerId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseStructure<String> deleteReview(@PathVariable int id) {

        service.deleteReview(id);

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Review deleted successfully",
                "Deleted ID: " + id
        );
    }
}