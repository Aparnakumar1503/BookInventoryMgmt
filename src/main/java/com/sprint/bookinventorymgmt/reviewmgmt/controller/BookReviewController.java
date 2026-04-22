package com.sprint.bookinventorymgmt.reviewmgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.service.BookReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books/{isbn}/reviews")
public class BookReviewController {

    private final BookReviewService service;

    public BookReviewController(BookReviewService service) {
        this.service = service;
    }

    @PostMapping
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

    @GetMapping
    public ResponseStructure<List<BookReviewResponseDTO>> getByISBN(@PathVariable String isbn) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Reviews fetched successfully",
                service.getReviewsByISBN(isbn)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseStructure<String> deleteReview(@PathVariable int id) {
        service.deleteReview(id);

        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Review deleted successfully",
                "Deleted ID: " + id
        );
    }
}
