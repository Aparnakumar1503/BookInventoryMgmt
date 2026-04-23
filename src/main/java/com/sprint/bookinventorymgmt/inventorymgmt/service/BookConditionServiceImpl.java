package com.sprint.bookinventorymgmt.inventorymgmt.service;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.BookCondition;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.BookConditionNotFoundException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.DatabaseOperationException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InvalidConditionRankException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InvalidInventoryDataException;

import com.sprint.bookinventorymgmt.inventorymgmt.repository.IBookConditionRepository;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookConditionServiceImpl implements IBookConditionService {

    private final IBookConditionRepository repository;

    public BookConditionServiceImpl(IBookConditionRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookCondition saveBookCondition(BookCondition bookCondition) {

        if (bookCondition.getRanks() == null || bookCondition.getPrice() == null) {
            throw new InvalidInventoryDataException("Rank and Price cannot be null");
        }
        if (bookCondition.getRanks() <= 0) {
            throw new InvalidConditionRankException("Rank must be greater than zero");
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

        return ResponseBuilder.success(
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
        if (updated.getRanks() != null && updated.getRanks() <= 0) {
            throw new InvalidConditionRankException("Rank must be greater than zero");
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

        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "BookCondition updated successfully",
                saved
        );
    }
}
