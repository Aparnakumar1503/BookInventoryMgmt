package com.sprint.bookinventorymgmt.reviewmgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.service.BookReviewService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class BookReviewController {
    @Autowired
    private BookReviewService service;

    public BookReviewController(BookReviewService service) {
        this.service = service;
    }

    @PostMapping("/api/v1/books/{isbn}/reviews")
    public ResponseStructure<BookReviewResponseDTO> addReview(
            @PathVariable @NotBlank(message = "ISBN cannot be blank") @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters") String isbn,
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
            @PathVariable @NotBlank(message = "ISBN cannot be blank") @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters") String isbn,
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully",
                PaginationUtils.paginate(service.getReviewsByISBN(isbn), page, size)
        );
    }

    @GetMapping("/api/v1/reviews/isbn/{isbn}")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getByIsbnQuery(
            @PathVariable @NotBlank(message = "ISBN cannot be blank") @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters") String isbn,
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully by ISBN",
                PaginationUtils.paginate(service.getReviewsByISBN(isbn), page, size)
        );
    }

    @GetMapping("/api/v1/reviews/reviewer/{reviewerId}")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getByReviewer(
            @PathVariable @Positive(message = "Reviewer ID must be greater than 0") int reviewerId,
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully by reviewer",
                PaginationUtils.paginate(service.getReviewsByReviewer(reviewerId), page, size)
        );
    }

    @GetMapping("/api/v1/reviews/max-rating")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getMaxRatingReviews(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully by max rating",
                PaginationUtils.paginate(service.getReviewsWithMaxRating(), page, size)
        );
    }

    @DeleteMapping("/api/v1/reviews/{id}")
    public ResponseStructure<BookReviewResponseDTO> deleteReview(
            @PathVariable @Positive(message = "Review ID must be greater than 0") int id) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Review deleted successfully",
                service.deleteReview(id)
        );
    }
}
