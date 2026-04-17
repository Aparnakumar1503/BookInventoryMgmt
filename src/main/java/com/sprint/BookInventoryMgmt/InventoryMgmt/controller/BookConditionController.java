package com.sprint.BookInventoryMgmt.InventoryMgmt.controller;

import com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto.InventoryMapper;
import com.sprint.BookInventoryMgmt.InventoryMgmt.responsedto.BookConditionResponseDTO;
import com.sprint.BookInventoryMgmt.InventoryMgmt.service.BookConditionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book-conditions")
public class BookConditionController {

    @Autowired
    private BookConditionService service;

    @GetMapping
    public ResponseEntity<List<BookConditionResponseDTO>> getAll() {
        return ResponseEntity.ok(
                service.getAllBookConditions().stream()
                        .map(InventoryMapper::toBookConditionResponse)
                        .toList()
        );
    }

    @GetMapping("/{rank}")
    public ResponseEntity<BookConditionResponseDTO> getByRank(@PathVariable Integer rank) {
        return ResponseEntity.ok(
                InventoryMapper.toBookConditionResponse(service.getByRank(rank))
        );
    }
}