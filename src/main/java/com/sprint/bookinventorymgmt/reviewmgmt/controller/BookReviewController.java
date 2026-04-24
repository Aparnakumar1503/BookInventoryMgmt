package com.sprint.bookinventorymgmt.reviewmgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.service.BookReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles book-scoped review endpoints so review creation and lookup stay tied to the book resource.
 */
@RestController
public class BookReviewController {

    private final BookReviewService bookReviewService;

    public BookReviewController(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    /**
     * Returns reviews for one book.
     */
    @GetMapping("/api/v1/books/{isbn}/reviews")
    public ResponseStructure<PaginatedResponse<BookReviewResponseDTO>> getReviewsByBook(
            @PathVariable String isbn,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully",
                PaginationUtils.paginate(bookReviewService.getReviewsByISBN(isbn), page, size)
        );
    }

    /**
     * Creates a review for one book.
     */
    @PostMapping("/api/v1/books/{isbn}/reviews")
    public ResponseStructure<BookReviewResponseDTO> addReview(
            @PathVariable String isbn,
            @Valid @RequestBody BookReviewRequestDTO dto) {
        dto.setIsbn(isbn);
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Review added successfully",
                bookReviewService.addReview(dto)
        );
    }
}
