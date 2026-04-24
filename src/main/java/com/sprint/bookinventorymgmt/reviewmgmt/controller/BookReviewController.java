package com.sprint.bookinventorymgmt.reviewmgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.service.BookReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookReviewController {
    @Autowired
    private BookReviewService service;

    public BookReviewController(BookReviewService service) {
        this.service = service;
    }

    @PostMapping("/api/v1/books/{isbn}/reviews")
    public ResponseStructure<BookReviewResponseDTO> addReview(
            @PathVariable String isbn,
            @Valid @RequestBody BookReviewRequestDTO dto) {
        dto.setIsbn(isbn);

        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Review added successfully",
                service.addReview(dto)
        );
    }

    @GetMapping("/api/v1/books/{isbn}/reviews")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getByISBN(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully",
                PaginationUtils.paginate(service.getReviewsByISBN(isbn), page, size)
        );
    }

    @GetMapping("/api/v1/reviews/isbn/{isbn}")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getByIsbnQuery(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully by ISBN",
                PaginationUtils.paginate(service.getReviewsByISBN(isbn), page, size)
        );
    }

    @GetMapping("/api/v1/reviews/reviewer/{reviewerId}")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getByReviewer(
            @PathVariable int reviewerId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully by reviewer",
                PaginationUtils.paginate(service.getReviewsByReviewer(reviewerId), page, size)
        );
    }

    @GetMapping("/api/v1/reviews/max-rating")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getMaxRatingReviews(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully by max rating",
                PaginationUtils.paginate(service.getReviewsWithMaxRating(), page, size)
        );
    }

    @DeleteMapping("/api/v1/reviews/{id}")
    public ResponseStructure<BookReviewResponseDTO> deleteReview(@PathVariable int id) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Review deleted successfully",
                service.deleteReview(id)
        );
    }
}
