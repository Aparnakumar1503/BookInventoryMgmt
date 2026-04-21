package com.sprint.bookinventorymgmt.authormgmt.controller;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.service.IAuthorService;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
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

    @GetMapping
    public ResponseEntity<ResponseStructure<List<AuthorResponseDTO>>> getAllAuthors() {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Authors fetched successfully",
                        service.getAllAuthors()
                )
        );
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<ResponseStructure<AuthorResponseDTO>> getAuthorById(@PathVariable Integer authorId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Author fetched successfully",
                        service.getAuthorById(authorId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<AuthorResponseDTO>> addAuthor(
            @Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED.value(),
                                "Author created successfully",
                                service.addAuthor(requestDTO)
                        )
                );
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<ResponseStructure<AuthorResponseDTO>> updateAuthor(
            @PathVariable Integer authorId,
            @Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Author updated successfully",
                        service.updateAuthor(authorId, requestDTO)
                )
        );
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<ResponseStructure<String>> deleteAuthor(@PathVariable Integer authorId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Author deleted successfully",
                        service.deleteAuthor(authorId)
                )
        );
    }
}
