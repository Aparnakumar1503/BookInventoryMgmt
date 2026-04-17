package com.sprint.BookInventoryMgmt.InventoryMgmt.controller;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;
import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.BookConditionRequestDTO;
import com.sprint.BookInventoryMgmt.InventoryMgmt.responsedto.BookConditionResponseDTO;
import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.InventoryMapper;
import com.sprint.BookInventoryMgmt.InventoryMgmt.service.BookConditionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book-conditions")
public class BookConditionController {

    @Autowired
    private BookConditionService service;

    // ✅ POST API (NEW)
    @PostMapping
    public ResponseEntity<BookConditionResponseDTO> create(
            @RequestBody BookConditionRequestDTO dto) {

        BookCondition saved = service.saveBookCondition(
                InventoryMapper.toBookConditionEntity(dto)
        );

        return ResponseEntity.status(201)
                .body(InventoryMapper.toBookConditionResponse(saved));
    }

    // GET all
    @GetMapping
    public ResponseEntity<List<BookConditionResponseDTO>> getAll() {
        return ResponseEntity.ok(
                service.getAllBookConditions().stream()
                        .map(InventoryMapper::toBookConditionResponse)
                        .toList()
        );
    }

    // GET by rank
    @GetMapping("/{rank}")
    public ResponseEntity<BookConditionResponseDTO> getByRank(@PathVariable Integer rank) {
        return ResponseEntity.ok(
                InventoryMapper.toBookConditionResponse(service.getByRank(rank))
        );
    }
}