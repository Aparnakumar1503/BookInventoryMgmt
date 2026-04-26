package com.sprint.bookinventorymgmt.inventorymgmt.service;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.BookCondition;
import com.sprint.bookinventorymgmt.common.ResponseStructure;

import java.util.List;

public interface IBookConditionService {

    BookCondition saveBookCondition(BookCondition bookCondition);

    List<BookCondition> getAllBookConditions();

    BookCondition getById(Integer id);

    ResponseStructure<String> deleteBookCondition(Integer id);

    ResponseStructure<BookCondition> updateBookCondition(Integer id, BookCondition updated);
}