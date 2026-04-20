package com.sprint.BookInventoryMgmt.authormgmt.controller;

import com.sprint.BookInventoryMgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.BookInventoryMgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.BookInventoryMgmt.authormgmt.service.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final IAuthorService service;

    public AuthorController(IAuthorService service) {
        this.service = service;
    }

    // GET /api/v1/authors
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        return ResponseEntity.ok(service.getAllAuthors());
    }

    // GET /api/v1/authors/{authorId}
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Integer authorId) {
        return ResponseEntity.ok(service.getAuthorById(authorId));
    }

    // POST /api/v1/authors
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> addAuthor(@Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addAuthor(requestDTO));
    }

    // PUT /api/v1/authors/{authorId}
    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable Integer authorId,
                                                          @Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseEntity.ok(service.updateAuthor(authorId, requestDTO));
    }

    // DELETE /api/v1/authors/{authorId}
    @DeleteMapping("/{authorId}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Integer authorId) {
        return ResponseEntity.ok(service.deleteAuthor(authorId));
    }
}
