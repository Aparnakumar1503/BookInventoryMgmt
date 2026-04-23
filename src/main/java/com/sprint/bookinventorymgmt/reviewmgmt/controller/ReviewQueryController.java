package com.sprint.bookinventorymgmt.reviewmgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.service.BookReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewQueryController {

    private final BookReviewService reviewService;

    public ReviewQueryController(BookReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getReviewsByIsbn(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully by ISBN",
                PaginationUtils.paginate(reviewService.getReviewsByISBN(isbn), page, size)
        );
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getReviewsByReviewer(
            @PathVariable Integer reviewerId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully by reviewer",
                PaginationUtils.paginate(reviewService.getReviewsByReviewer(reviewerId), page, size)
        );
    }

    @GetMapping("/max-rating")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getReviewsWithMaxRating(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Highest rated reviews fetched successfully",
                PaginationUtils.paginate(reviewService.getReviewsWithMaxRating(), page, size)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseStructure<BookReviewResponseDTO> deleteReview(@PathVariable Integer id) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Review deleted successfully",
                reviewService.deleteReview(id)
        );
    }
}
