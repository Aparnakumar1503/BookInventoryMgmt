package com.sprint.bookinventorymgmt.authormgmt.controller;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.authormgmt.service.IAuthorService;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@Validated
public class AuthorController {

    @Autowired
    private  IAuthorService service;

    public AuthorController(IAuthorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseStructure<PaginatedResponse<AuthorResponseDTO>> getAllAuthors(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Authors fetched successfully",
                PaginationUtils.paginate(service.getAllAuthors(), page, size)
        );
    }

    @GetMapping("/{authorId}")
    public ResponseStructure<AuthorResponseDTO> getAuthorById(
            @PathVariable @Positive(message = "Author ID must be greater than 0") Integer authorId) {
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
    public ResponseStructure<AuthorResponseDTO> updateAuthor(
            @PathVariable @Positive(message = "Author ID must be greater than 0") Integer authorId,
            @Valid @RequestBody AuthorRequestDTO requestDTO) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Author updated successfully",
                service.updateAuthor(authorId, requestDTO)
        );
    }

    @DeleteMapping("/{authorId}")
    public ResponseStructure<String> deleteAuthor(
            @PathVariable @Positive(message = "Author ID must be greater than 0") Integer authorId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Author deleted successfully",
                service.deleteAuthor(authorId)
        );
    }
}
