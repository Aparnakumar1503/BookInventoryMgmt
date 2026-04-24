package com.sprint.bookinventorymgmt.inventorymgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.BookConditionRequestDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.InventoryMapper;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.responsedto.BookConditionResponseDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.entity.BookCondition;
import com.sprint.bookinventorymgmt.inventorymgmt.service.IBookConditionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Keeps book condition reference endpoints separate from inventory copy operations.
 */
@RestController
public class BookConditionController {

    private final IBookConditionService bookConditionService;

    public BookConditionController(IBookConditionService bookConditionService) {
        this.bookConditionService = bookConditionService;
    }

    /**
     * Creates one book condition record.
     */
    @PostMapping("/api/v1/book-conditions")
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> createBookCondition(
            @Valid @RequestBody BookConditionRequestDTO dto) {
        BookCondition saved = bookConditionService.saveBookCondition(InventoryMapper.toBookConditionEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBuilder.success(
                        HttpStatus.CREATED.value(),
                        "Book condition created successfully",
                        InventoryMapper.toBookConditionResponse(saved)
                )
        );
    }

    /**
     * Returns all book conditions used by inventory pricing and grading.
     */
    @GetMapping("/api/v1/book-conditions")
    public ResponseEntity<ResponseStructure<PaginatedResponse<BookConditionResponseDTO>>> getAllBookConditions(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        List<BookConditionResponseDTO> response = bookConditionService.getAllBookConditions().stream()
                .map(InventoryMapper::toBookConditionResponse)
                .toList();

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Book conditions fetched successfully",
                        PaginationUtils.paginate(response, page, size)
                )
        );
    }

    /**
     * Returns one book condition by rank.
     */
    @GetMapping("/api/v1/book-conditions/{rank}")
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> getBookConditionByRank(@PathVariable Integer rank) {
        BookCondition condition = bookConditionService.getById(rank);
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Book condition fetched successfully",
                        InventoryMapper.toBookConditionResponse(condition)
                )
        );
    }

    /**
     * Updates one book condition by rank.
     */
    @PutMapping("/api/v1/book-conditions/{rank}")
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> updateBookCondition(
            @PathVariable Integer rank,
            @Valid @RequestBody BookConditionRequestDTO dto) {
        ResponseStructure<BookCondition> result = bookConditionService.updateBookCondition(
                rank,
                InventoryMapper.toBookConditionEntity(dto)
        );

        return ResponseEntity.status(result.getStatusCode()).body(
                ResponseBuilder.success(
                        result.getStatusCode(),
                        result.getMessage(),
                        InventoryMapper.toBookConditionResponse(result.getData())
                )
        );
    }

    /**
     * Deletes one book condition by rank.
     */
    @DeleteMapping("/api/v1/book-conditions/{rank}")
    public ResponseEntity<ResponseStructure<String>> deleteBookCondition(@PathVariable Integer rank) {
        ResponseStructure<String> result = bookConditionService.deleteBookCondition(rank);
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }
}
