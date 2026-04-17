package com.sprint.BookInventoryMgmt.reviewmgmt.controller;

import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.BookReviewRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.BookReviewResponseDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.service.BookReviewService;
import com.sprint.BookInventoryMgmt.reviewmgmt.util.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class BookReviewController {

    @Autowired
    private BookReviewService service;

    @PostMapping("/add")
    public ResponseStructure<BookReviewResponseDTO> addReview(
            @RequestBody BookReviewRequestDTO dto) {

        return new ResponseStructure<>(
                201,
                "Book review added successfully",
                service.addReview(dto)
        );
    }

    @GetMapping
    public ResponseStructure<List<BookReviewResponseDTO>> getAllReviews() {

        return new ResponseStructure<>(
                200,
                "All reviews fetched successfully",
                service.getAllReviews()
        );
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseStructure<List<BookReviewResponseDTO>> getByISBN(
            @PathVariable String isbn) {

        return new ResponseStructure<>(
                200,
                "Reviews fetched by ISBN",
                service.getReviewsByISBN(isbn)
        );
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseStructure<List<BookReviewResponseDTO>> getByReviewer(
            @PathVariable int reviewerId) {

        return new ResponseStructure<>(
                200,
                "Reviews fetched by reviewer",
                service.getReviewsByReviewer(reviewerId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseStructure<String> deleteReview(@PathVariable int id) {

        service.deleteReview(id);

        return new ResponseStructure<>(
                200,
                "Review deleted successfully",
                "Deleted ID: " + id
        );
    }
}