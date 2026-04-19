package com.sprint.BookInventoryMgmt.authormgmt.controller;

import com.sprint.BookInventoryMgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.BookInventoryMgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.BookInventoryMgmt.authormgmt.service.IBookAuthorService;
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

    // POST /api/v1/books/{isbn}/authors/{authorId}
    @PostMapping("/{isbn}/authors/{authorId}")
    public ResponseEntity<BookAuthorResponseDTO> addBookAuthor(@PathVariable String isbn,
                                                               @PathVariable Integer authorId,
                                                               @Valid @RequestBody BookAuthorRequestDTO requestDTO) {
        requestDTO.setIsbn(isbn);
        requestDTO.setAuthorId(authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBookAuthor(requestDTO));
    }

    // DELETE /api/v1/books/{isbn}/authors/{authorId}
    @DeleteMapping("/{isbn}/authors/{authorId}")
    public ResponseEntity<String> deleteBookAuthor(@PathVariable String isbn,
                                                   @PathVariable Integer authorId) {
        return ResponseEntity.ok(service.deleteByIsbn(isbn));
    }
}
