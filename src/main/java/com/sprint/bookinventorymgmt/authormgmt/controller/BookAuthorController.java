package com.sprint.bookinventorymgmt.authormgmt.controller;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.service.IBookAuthorService;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookAuthorController {

    private final IBookAuthorService service;

    public BookAuthorController(IBookAuthorService service) {
        this.service = service;
    }

    @PostMapping("/{isbn}/authors/{authorId}")
    public ResponseEntity<ResponseStructure<BookAuthorResponseDTO>> addBookAuthor(
            @PathVariable String isbn,
            @PathVariable Integer authorId,
            @Valid @RequestBody BookAuthorRequestDTO requestDTO) {
        requestDTO.setIsbn(isbn);
        requestDTO.setAuthorId(authorId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED.value(),
                                "Book author mapping created successfully",
                                service.addBookAuthor(requestDTO)
                        )
                );
    }

    @DeleteMapping("/{isbn}/authors/{authorId}")
    public ResponseEntity<ResponseStructure<String>> deleteBookAuthor(
            @PathVariable String isbn,
            @PathVariable Integer authorId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Book author mappings deleted successfully",
                        service.deleteByIsbn(isbn)
                )
        );
    }
}
