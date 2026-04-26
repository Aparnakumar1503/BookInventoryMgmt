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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/book-conditions")
@Validated
public class BookConditionController {

    @Autowired
    private  IBookConditionService service;

    public BookConditionController(IBookConditionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> create(
            @Valid @RequestBody BookConditionRequestDTO dto) {
        BookCondition saved = service.saveBookCondition(InventoryMapper.toBookConditionEntity(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBuilder.success(
                        HttpStatus.CREATED.value(),
                        "Book condition created successfully",
                        InventoryMapper.toBookConditionResponse(saved)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<PaginatedResponse<BookConditionResponseDTO>>> getAll(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
        List<BookCondition> conditions = service.getAllBookConditions();
        List<BookConditionResponseDTO> list = new ArrayList<>();
        for (BookCondition condition : conditions) {
            list.add(InventoryMapper.toBookConditionResponse(condition));
        }

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Book conditions fetched successfully",
                        PaginationUtils.paginate(list, page, size)
                )
        );
    }

    @GetMapping("/{rank}")
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> getByRank(
            @PathVariable @Positive(message = "Rank must be greater than 0") Integer rank) {
        BookConditionResponseDTO dto = InventoryMapper.toBookConditionResponse(service.getById(rank));

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Book condition fetched successfully",
                        dto
                )
        );
    }

    @PutMapping("/{rank}")
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> update(
            @PathVariable @Positive(message = "Rank must be greater than 0") Integer rank,
            @Valid @RequestBody BookConditionRequestDTO dto) {
        BookCondition updated = InventoryMapper.toBookConditionEntity(dto);
        ResponseStructure<BookCondition> result = service.updateBookCondition(rank, updated);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        result.getStatusCode(),
                        result.getMessage(),
                        InventoryMapper.toBookConditionResponse(result.getData())
                )
        );
    }

    @DeleteMapping("/{rank}")
    public ResponseEntity<ResponseStructure<String>> delete(
            @PathVariable @Positive(message = "Rank must be greater than 0") Integer rank) {
        return ResponseEntity.ok(service.deleteBookCondition(rank));
    }
}
