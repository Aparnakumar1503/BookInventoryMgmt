package com.sprint.bookinventorymgmt.authormgmt.controller;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.service.IBookAuthorService;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles book-author association endpoints separately from author CRUD because the mapping is its own entity.
 */
@RestController
public class BookAuthorController {

    private final IBookAuthorService bookAuthorService;

    public BookAuthorController(IBookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    /**
     * Creates a mapping between a book and an author.
     */
    @PostMapping("/api/v1/books/{isbn}/authors/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<BookAuthorResponseDTO> addBookAuthor(
            @PathVariable String isbn,
            @PathVariable Integer authorId,
            @Valid @RequestBody BookAuthorRequestDTO requestDTO) {
        requestDTO.setIsbn(isbn);
        requestDTO.setAuthorId(authorId);
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Book author mapping created successfully",
                bookAuthorService.addBookAuthor(requestDTO)
        );
    }

    /**
     * Deletes a mapping for the supplied book and author path.
     */
    @DeleteMapping("/api/v1/books/{isbn}/authors/{authorId}")
    public ResponseStructure<String> deleteBookAuthor(
            @PathVariable String isbn,
            @PathVariable Integer authorId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Book author mapping deleted successfully",
                bookAuthorService.deleteByIsbn(isbn)
        );
    }
}
