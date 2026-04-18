package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.BookConditionNotFoundException;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.DatabaseOperationException;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.InvalidInventoryDataException;
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

        if (bookCondition.getRanks() == null || bookCondition.getPrice() == null) {
            throw new InvalidInventoryDataException("Rank and Price cannot be null");
        }

        try {
            return repository.save(bookCondition);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to save BookCondition");
        }
    }

    @Override
    public List<BookCondition> getAllBookConditions() {
        return repository.findAll();
    }

    @Override
    public BookCondition getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new BookConditionNotFoundException("BookCondition not found with id: " + id));
    }

    @Override
    public ResponseStructure<String> deleteBookCondition(Integer id) {

        BookCondition bc = repository.findById(id)
                .orElseThrow(() ->
                        new BookConditionNotFoundException("BookCondition not found with id: " + id));

        try {
            repository.delete(bc);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete BookCondition");
        }

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "BookCondition deleted successfully",
                "Deleted ID: " + id
        );
    }

    @Override
    public ResponseStructure<BookCondition> updateBookCondition(Integer id, BookCondition updated) {

        if (updated.getPrice() == null) {
            throw new InvalidInventoryDataException("Price cannot be null");
        }

        BookCondition existing = repository.findById(id)
                .orElseThrow(() ->
                        new BookConditionNotFoundException("BookCondition not found with id: " + id));

        existing.setDescription(updated.getDescription());
        existing.setFullDescription(updated.getFullDescription());
        existing.setPrice(updated.getPrice());

        BookCondition saved;

        try {
            saved = repository.save(existing);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update BookCondition");
        }

        return new ResponseStructure<>(
                HttpStatus.OK.value(),
                "BookCondition updated successfully",
                saved
        );
    }
}