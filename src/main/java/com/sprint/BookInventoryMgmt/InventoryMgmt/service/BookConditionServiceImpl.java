package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.ResourceNotFoundException;
import com.sprint.BookInventoryMgmt.InventoryMgmt.repository.BookConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookConditionServiceImpl implements BookConditionService {

    @Autowired
    private BookConditionRepository repository;

    @Override
    public BookCondition saveBookCondition(BookCondition bookCondition) {
        return repository.save(bookCondition);
    }

    @Override
    public List<BookCondition> getAllBookConditions() {
        return repository.findAll();
    }

    @Override
    public BookCondition getByRank(Integer rank) {
        return repository.findById(rank)
                .orElseThrow(() -> new ResourceNotFoundException("BookCondition not found with rank: " + rank));
    }
}