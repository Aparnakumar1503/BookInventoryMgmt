package com.sprint.BookInventoryMgmt.Inventory.Controller;

import com.sprint.BookInventoryMgmt.Inventory.Entity.BookCondition;
import com.sprint.BookInventoryMgmt.Inventory.Repository.BookConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book-conditions")
public class BookConditionController {

    @Autowired
    private BookConditionRepository repository;

    @GetMapping
    public List<BookCondition> getAllConditions() {
        return repository.findAll();
    }

    @GetMapping("/{rank}")
    public BookCondition getByRank(@PathVariable Integer rank) {
        return repository.findById(rank).orElseThrow();
    }
}