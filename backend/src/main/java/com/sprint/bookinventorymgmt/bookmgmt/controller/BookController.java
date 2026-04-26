package com.sprint.bookinventorymgmt.bookmgmt.controller;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.BookRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.BookResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.service.IBookService;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book APIs", description = "CRUD operations for Books")
@Validated
public class BookController {

    @Autowired
    private  IBookService IBookService;

    public BookController(IBookService IBookService) {
        this.IBookService = IBookService;
    }

    @Operation(summary = "Create a new book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO dto) {

        BookResponseDTO response = IBookService.createBook(dto);

        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Book created successfully",
                response
        );
    }

    @Operation(summary = "Get book by ISBN")
    @GetMapping("/{isbn}")
    public ResponseStructure<BookResponseDTO> get(
            @PathVariable @NotBlank(message = "ISBN cannot be blank") @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters") String isbn) {

        BookResponseDTO response = IBookService.getBookByIsbn(isbn);

        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Book fetched successfully",
                response
        );
    }

    @Operation(summary = "Get all books or filter by category and publisher")
    @GetMapping
    public ResponseStructure<PaginatedResponse<BookResponseDTO>> getAll(
            @RequestParam(required = false) @Positive(message = "Category ID must be greater than 0") Integer categoryId,
            @RequestParam(required = false) @Positive(message = "Publisher ID must be greater than 0") Integer publisherId,
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {

        List<BookResponseDTO> response;

        if (categoryId != null && publisherId != null) {
            response = IBookService.getBooksByCategoryAndPublisher(categoryId, publisherId);

        } else if (categoryId != null) {
            response = IBookService.getBooksByCategory(categoryId);   // 🔥 ADD THIS

        } else if (publisherId != null) {
            response = IBookService.getBooksByPublisher(publisherId); // 🔥 ADD THIS

        } else {
            response = IBookService.getAllBooks();
        }

        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Books fetched successfully",
                PaginationUtils.paginate(response, page, size)
        );
    }

    @Operation(summary = "Update book")
    @PutMapping("/{isbn}")
    public ResponseStructure<BookResponseDTO> update(
            @PathVariable @NotBlank(message = "ISBN cannot be blank") @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters") String isbn,
            @Valid @RequestBody BookRequestDTO dto) {

        BookResponseDTO response = IBookService.updateBook(isbn, dto);

        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Book updated successfully",
                response
        );
    }

    @Operation(summary = "Delete book")
    @DeleteMapping("/{isbn}")
    public ResponseStructure<String> delete(
            @PathVariable @NotBlank(message = "ISBN cannot be blank") @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters") String isbn) {

        String response = IBookService.deleteBook(isbn);

        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Book deleted successfully",
                response
        );
    }
}
