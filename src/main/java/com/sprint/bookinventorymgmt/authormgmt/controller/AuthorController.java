package com.sprint.bookinventorymgmt.authormgmt.controller;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.service.IAuthorService;
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

@RestController
public class AuthorController {

    private final IAuthorService authorService;

    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Returns all authors.
     */
    @GetMapping("/api/v1/authors")
    public ResponseStructure<PaginatedResponse<AuthorResponseDTO>> getAllAuthors(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Authors fetched successfully",
                PaginationUtils.paginate(authorService.getAllAuthors(), page, size)
        );
    }

    /**
     * Returns one author by identifier.
     */
    @GetMapping("/api/v1/authors/{authorId}")
    public ResponseStructure<AuthorResponseDTO> getAuthorById(@PathVariable Integer authorId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Author fetched successfully",
                authorService.getAuthorById(authorId)
        );
    }

    /**
     * Creates an author.
     */
    @PostMapping("/api/v1/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<AuthorResponseDTO> addAuthor(@Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Author created successfully",
                authorService.addAuthor(requestDTO)
        );
    }

    /**
     * Updates an existing author.
     */
    @PutMapping("/api/v1/authors/{authorId}")
    public ResponseStructure<AuthorResponseDTO> updateAuthor(
            @PathVariable Integer authorId,
            @Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Author updated successfully",
                authorService.updateAuthor(authorId, requestDTO)
        );
    }

    /**
     * Deletes an author.
     */
    @DeleteMapping("/api/v1/authors/{authorId}")
    public ResponseStructure<String> deleteAuthor(@PathVariable Integer authorId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Author deleted successfully",
                authorService.deleteAuthor(authorId)
        );
    }
}
