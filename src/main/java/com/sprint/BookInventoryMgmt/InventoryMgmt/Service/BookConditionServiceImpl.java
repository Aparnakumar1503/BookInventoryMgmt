package com.sprint.BookInventoryMgmt.InventoryMgmt.Service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.BookCondition;
import com.sprint.BookInventoryMgmt.InventoryMgmt.Exception.ResourceNotFoundException;
import com.sprint.BookInventoryMgmt.InventoryMgmt.Repository.BookConditionRepository;
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

    @Override
    public List<BookCondition> getByPriceRange(double min, double max) {
        return repository.findByPriceBetween(min, max);
    }

    @Override
    public List<BookCondition> getByRankGreaterThan(int rank) {
        return repository.findByRanksGreaterThan(rank);
    }
}