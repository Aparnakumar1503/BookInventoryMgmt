package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;

import java.util.List;

public interface BookConditionService {

    List<BookCondition> getAllBookConditions();
    BookCondition getByRank(Integer rank);
}