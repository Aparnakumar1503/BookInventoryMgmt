package com.sprint.bookinventorymgmt.authormgmt.controller;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.service.IBookAuthorService;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@Validated
public class BookAuthorController {

    @Autowired
    private IBookAuthorService service;

    public BookAuthorController(IBookAuthorService service) {
        this.service = service;
    }

    @PostMapping("/{isbn}/authors/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<BookAuthorResponseDTO> addBookAuthor(
            @PathVariable @NotBlank(message = "ISBN cannot be blank") @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters") String isbn,
            @PathVariable @Positive(message = "Author ID must be greater than 0") Integer authorId,
            @Valid @RequestBody BookAuthorRequestDTO requestDTO) {
        requestDTO.setIsbn(isbn);
        requestDTO.setAuthorId(authorId);
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Book author mapping created successfully",
                service.addBookAuthor(requestDTO)
        );
    }

    @DeleteMapping("/{isbn}/authors/{authorId}")
    public ResponseStructure<String> deleteBookAuthor(
            @PathVariable @NotBlank(message = "ISBN cannot be blank") @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters") String isbn,
            @PathVariable @Positive(message = "Author ID must be greater than 0") Integer authorId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Book author mapping deleted successfully",
                service.deleteByIsbnAndAuthorId(isbn, authorId)
        );
    }
}
