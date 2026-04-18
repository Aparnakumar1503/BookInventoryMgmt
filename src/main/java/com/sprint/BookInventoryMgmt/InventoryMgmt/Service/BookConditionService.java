package com.sprint.BookInventoryMgmt.InventoryMgmt.Service;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.BookCondition;
import java.util.List;

public interface BookConditionService {

    BookCondition saveBookCondition(BookCondition bookCondition);

    List<BookCondition> getAllBookConditions();

    BookCondition getByRank(Integer rank);

    List<BookCondition> getByPriceRange(double min, double max);

    List<BookCondition> getByRankGreaterThan(int rank);
}