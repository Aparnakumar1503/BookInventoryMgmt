package com.sprint.BookInventoryMgmt.AuthorMgmt.Controller;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Service.BookAuthorService;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthorId;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-authors")
public class BookAuthorController {

    private final BookAuthorService service;

    public BookAuthorController(BookAuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseStructure<BookAuthorResponseDTO> assignAuthorToBook(
            @RequestBody BookAuthorRequestDTO dto) {

        BookAuthorResponseDTO response = service.assignAuthorToBook(dto);

        return new ResponseStructure<>(
                201,
                "Author assigned to book successfully",
                response
        );
    }

    @GetMapping
    public ResponseStructure<List<BookAuthorResponseDTO>> getAllMappings() {

        List<BookAuthorResponseDTO> list = service.getAllMappings();

        return new ResponseStructure<>(
                200,
                "All mappings fetched successfully",
                list
        );
    }

    @DeleteMapping("/{isbn}/{authorId}")
    public ResponseStructure<String> deleteMapping(
            @PathVariable String isbn,
            @PathVariable Integer authorId) {

        BookAuthorId id = new BookAuthorId(isbn, authorId);
        service.removeMapping(id);

        return new ResponseStructure<>(
                200,
                "Mapping deleted successfully",
                null
        );
    }
}