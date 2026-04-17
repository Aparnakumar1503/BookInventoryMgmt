package com.sprint.BookInventoryMgmt.AuthorMgmt.Controller;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Service.AuthorService;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseStructure<AuthorResponseDTO> createAuthor(
            @Valid @RequestBody AuthorRequestDTO dto) {

        AuthorResponseDTO response = service.createAuthor(dto);

        return new ResponseStructure<>(
                201,
                "Author created successfully",
                response
        );
    }

    @GetMapping("/{id}")
    public ResponseStructure<AuthorResponseDTO> getAuthorById(@PathVariable Integer id) {

        AuthorResponseDTO response = service.getAuthorById(id);

        return new ResponseStructure<>(
                200,
                "Author fetched successfully",
                response
        );
    }

    @GetMapping
    public ResponseStructure<List<AuthorResponseDTO>> getAllAuthors() {

        List<AuthorResponseDTO> list = service.getAllAuthors();

        return new ResponseStructure<>(
                200,
                "All authors fetched successfully",
                list
        );
    }

    @PutMapping("/{id}")
    public ResponseStructure<AuthorResponseDTO> updateAuthor(
            @PathVariable Integer id,
            @RequestBody AuthorRequestDTO dto) {

        AuthorResponseDTO response = service.updateAuthor(id, dto);

        return new ResponseStructure<>(
                200,
                "Author updated successfully",
                response
        );
    }

    @DeleteMapping("/{id}")
    public ResponseStructure<String> deleteAuthor(@PathVariable Integer id) {

        service.deleteAuthor(id);

        return new ResponseStructure<>(
                200,
                "Author deleted successfully with id: " + id,
                null
        );
    }
}