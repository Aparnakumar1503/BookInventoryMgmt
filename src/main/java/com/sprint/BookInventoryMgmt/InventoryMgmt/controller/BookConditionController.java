package com.sprint.BookInventoryMgmt.InventoryMgmt.controller;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;
import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.BookConditionRequestDTO;
import com.sprint.BookInventoryMgmt.InventoryMgmt.responsedto.BookConditionResponseDTO;
import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.InventoryMapper;
import com.sprint.BookInventoryMgmt.InventoryMgmt.service.BookConditionService;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book-conditions")
public class BookConditionController {

    @Autowired
    private BookConditionService service;

    @PostMapping
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> create(
            @RequestBody BookConditionRequestDTO dto) {

        BookCondition saved = service.saveBookCondition(
                InventoryMapper.toBookConditionEntity(dto)
        );

        ResponseStructure<BookConditionResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("BookCondition created successfully");
        response.setData(InventoryMapper.toBookConditionResponse(saved));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

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

    @GetMapping("/{rank}")
    public ResponseEntity<ResponseStructure<BookConditionResponseDTO>> getByRank(@PathVariable Integer rank) {

        BookConditionResponseDTO dto =
                InventoryMapper.toBookConditionResponse(service.getByRank(rank));

        ResponseStructure<BookConditionResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("BookCondition fetched successfully");
        response.setData(dto);

        return ResponseEntity.ok(response);
    }
}