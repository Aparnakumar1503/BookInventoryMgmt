package com.sprint.BookInventoryMgmt.InventoryMgmt.controller;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;
import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.BookConditionRequestDTO;
import com.sprint.BookInventoryMgmt.InventoryMgmt.responsedto.BookConditionResponseDTO;
import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.InventoryMapper;
import com.sprint.BookInventoryMgmt.InventoryMgmt.service.BookConditionService;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book-conditions")
public class BookConditionController {

    @Autowired
    private BookConditionService service;

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
    public ResponseEntity<ResponseStructure<List<BookConditionResponseDTO>>> getAll() {

        List<BookConditionResponseDTO> list = service.getAllBookConditions()
                .stream()
                .map(InventoryMapper::toBookConditionResponse)
                .toList();

        ResponseStructure<List<BookConditionResponseDTO>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("BookConditions fetched successfully");
        response.setData(list);

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