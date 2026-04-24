package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.BookRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.BookResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.IBookService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Returns the company-required book list with optional category and publisher filters.
     */
    @GetMapping("/api/v1/books")
    public ResponseStructure<PaginatedResponse<BookResponseDTO>> getAllBooks(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer publisherId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<BookResponseDTO> response;
        if (categoryId != null && publisherId != null) {
            response = bookService.getBooksByCategoryAndPublisher(categoryId, publisherId);
        } else if (categoryId != null) {
            response = bookService.getBooksByCategory(categoryId);
        } else if (publisherId != null) {
            response = bookService.getBooksByPublisher(publisherId);
        } else {
            response = bookService.getAllBooks();
        }

        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Books fetched successfully",
                PaginationUtils.paginate(response, page, size)
        );
    }

    /**
     * Returns one book by ISBN.
     */
    @GetMapping("/api/v1/books/{isbn}")
    public ResponseStructure<BookResponseDTO> getBookByIsbn(@PathVariable String isbn) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Book fetched successfully",
                bookService.getBookByIsbn(isbn)
        );
    }

    /**
     * Creates a book using DTO validation before service processing.
     */
    @PostMapping("/api/v1/books")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO dto) {
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Book created successfully",
                bookService.createBook(dto)
        );
    }

    /**
     * Updates an existing book identified by ISBN.
     */
    @PutMapping("/api/v1/books/{isbn}")
    public ResponseStructure<BookResponseDTO> updateBook(
            @PathVariable String isbn,
            @Valid @RequestBody BookRequestDTO dto) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Book updated successfully",
                bookService.updateBook(isbn, dto)
        );
    }

    /**
     * Deletes a book identified by ISBN.
     */
    @DeleteMapping("/api/v1/books/{isbn}")
    public ResponseStructure<String> deleteBook(@PathVariable String isbn) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Book deleted successfully",
                bookService.deleteBook(isbn)
        );
    }
}
