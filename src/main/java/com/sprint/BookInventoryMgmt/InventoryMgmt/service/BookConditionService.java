package com.sprint.BookInventoryMgmt.InventoryMgmt.service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import java.util.List;

public interface BookConditionService {

    BookCondition saveBookCondition(BookCondition bookCondition);

    List<BookCondition> getAllBookConditions();

    BookCondition getById(Integer id);

    ResponseStructure<String> deleteBookCondition(Integer id);

    ResponseStructure<BookCondition> updateBookCondition(Integer id, BookCondition updated);
}