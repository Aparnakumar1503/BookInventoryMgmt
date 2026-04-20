package com.sprint.BookInventoryMgmt.inventorymgmt.service;

import com.sprint.BookInventoryMgmt.inventorymgmt.entity.BookCondition;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import java.util.List;

public interface IBookConditionService {

    BookCondition saveBookCondition(BookCondition bookCondition);

    List<BookCondition> getAllBookConditions();

    BookCondition getById(Integer id);

    ResponseStructure<String> deleteBookCondition(Integer id);

    ResponseStructure<BookCondition> updateBookCondition(Integer id, BookCondition updated);
}