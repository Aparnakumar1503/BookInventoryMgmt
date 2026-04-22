package com.sprint.bookinventorymgmt.inventorymgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.inventorymgmt.entity.BookCondition;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.BookConditionRequestDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.responsedto.BookConditionResponseDTO;
import com.sprint.bookinventorymgmt.inventorymgmt.dto.requestdto.InventoryMapper;
import com.sprint.bookinventorymgmt.inventorymgmt.service.IBookConditionService;
import com.sprint.bookinventorymgmt.common.ResponseStructure;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book-conditions")
public class BookConditionController {

    @Autowired
    private IBookConditionService service;

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> create(
            @Valid @RequestBody BookConditionRequestDTO dto) {

        BookCondition saved = service.saveBookCondition(
                InventoryMapper.toBookConditionEntity(dto)
        );

        ResponseStructure<BookConditionResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("BookCondition created successfully");
        response.setData(InventoryMapper.toBookConditionResponse(saved));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<ResponseStructure<PaginatedResponse<BookConditionResponseDTO>>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        List<BookCondition> conditions = service.getAllBookConditions();
        List<BookConditionResponseDTO> list = new java.util.ArrayList<>();
        for (BookCondition condition : conditions) {
            list.add(InventoryMapper.toBookConditionResponse(condition));
        }

        ResponseStructure<PaginatedResponse<BookConditionResponseDTO>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("BookConditions fetched successfully");
        response.setData(PaginationUtils.paginate(list, page, size));

        return ResponseEntity.ok(response);
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> getById(@PathVariable Integer id) {

        BookConditionResponseDTO dto =
                InventoryMapper.toBookConditionResponse(service.getById(id));

        ResponseStructure<BookConditionResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("BookCondition fetched successfully");
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody BookConditionRequestDTO dto) {

        BookCondition updated = InventoryMapper.toBookConditionEntity(dto);

        ResponseStructure<BookCondition> result =
                service.updateBookCondition(id, updated);

        ResponseStructure<BookConditionResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(result.getStatusCode());
        response.setMessage(result.getMessage());
        response.setData(InventoryMapper.toBookConditionResponse(result.getData()));

        return ResponseEntity.ok(response);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteBookCondition(id));
    }
}
