package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.ResourceNotFoundException;
import com.sprint.BookInventoryMgmt.InventoryMgmt.repository.BookConditionRepository;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Override
    public ResponseStructure<String> deleteBookCondition(Integer rank) {

        BookCondition bookCondition = repository.findById(rank)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "BookCondition not found with rank: " + rank));

        repository.delete(bookCondition);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("BookCondition deleted successfully");
        response.setData("Deleted rank: " + rank);

        return response;
    }

}