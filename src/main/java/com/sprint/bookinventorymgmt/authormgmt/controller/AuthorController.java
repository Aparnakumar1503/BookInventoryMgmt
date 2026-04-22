package com.sprint.bookinventorymgmt.authormgmt.controller;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.authormgmt.service.IAuthorService;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseStructure<PaginatedResponse<AuthorResponseDTO>> getAllAuthors(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Authors fetched successfully",
                PaginationUtils.paginate(service.getAllAuthors(), page, size)
        );
    }

    @GetMapping("/{authorId}")
    public ResponseStructure<AuthorResponseDTO> getAuthorById(@PathVariable Integer authorId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Author fetched successfully",
                service.getAuthorById(authorId)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseStructure<AuthorResponseDTO> addAuthor(@Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Author created successfully",
                service.addAuthor(requestDTO)
        );
    }

    @PutMapping("/{authorId}")
    public ResponseStructure<AuthorResponseDTO> updateAuthor(@PathVariable Integer authorId,
                                                             @Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Author updated successfully",
                service.updateAuthor(authorId, requestDTO)
        );
    }

    @DeleteMapping("/{authorId}")
    public ResponseStructure<String> deleteAuthor(@PathVariable Integer authorId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Author deleted successfully",
                service.deleteAuthor(authorId)
        );
    }
}
