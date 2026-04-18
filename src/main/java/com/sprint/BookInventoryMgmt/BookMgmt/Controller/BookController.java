package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

import com.sprint.BookInventoryMgmt.BookMgmt.DTO.request.BookRequestDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.DTO.response.BookResponseDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.Service.BookService;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book APIs", description = "CRUD operations for Books")
public class BookController {

    private final BookService bookService;

    // Constructor Injection
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // CREATE
    @Operation(summary = "Create a new book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<BookResponseDTO> create(
            @Valid @RequestBody BookRequestDTO dto) {

        BookResponseDTO data = bookService.createBook(dto);

        return new ResponseStructure<>(
                HttpStatus.CREATED.value(),
                "Book created successfully",
                data
        );
    }

    // GET BY ISBN
    @Operation(summary = "Get book by ISBN")
    @GetMapping("/{isbn}")
    public ResponseStructure<BookResponseDTO> get(
            @PathVariable String isbn) {

        BookResponseDTO data =
                bookService.getBookByIsbn(isbn);

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Book fetched successfully",
                data
        );
    }

    // GET ALL
    @Operation(summary = "Get all books or filter by category & publisher")
    @GetMapping
    public ResponseStructure<List<BookResponseDTO>> getAll(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer publisherId) {

        List<BookResponseDTO> data;

        if (categoryId != null && publisherId != null) {

            data = bookService.getBooksByCategoryAndPublisher(
                    categoryId,
                    publisherId
            );

        } else {

            data = bookService.getAllBooks();
        }

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Books fetched successfully",
                data
        );
    }

    // UPDATE
    @Operation(summary = "Update book")
    @PutMapping("/{isbn}")
    public ResponseStructure<BookResponseDTO> update(
            @PathVariable String isbn,
            @Valid @RequestBody BookRequestDTO dto) {

        BookResponseDTO data =
                bookService.updateBook(isbn, dto);

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Book updated successfully",
                data
        );
    }

    // DELETE
    @Operation(summary = "Delete book")
    @DeleteMapping("/{isbn}")
    public ResponseStructure<String> delete(
            @PathVariable String isbn) {

        bookService.deleteBook(isbn);

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "Book deleted successfully",
                "Deleted ISBN: " + isbn
        );
    }

}