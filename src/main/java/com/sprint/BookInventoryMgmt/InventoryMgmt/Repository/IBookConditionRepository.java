package com.sprint.BookInventoryMgmt.inventorymgmt.repository;
import com.sprint.BookInventoryMgmt.inventorymgmt.entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBookConditionRepository extends JpaRepository<BookCondition, Integer> {

    List<BookCondition> findByPriceBetween(double min, double max);

    List<BookCondition> findByRanksGreaterThan(int rank);

    @Query("SELECT b FROM BookCondition b WHERE b.price > :price")
    List<BookCondition> getBookConditionsAbovePrice(double price);
}
