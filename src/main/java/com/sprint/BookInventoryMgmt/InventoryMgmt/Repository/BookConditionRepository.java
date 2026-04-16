package com.sprint.BookInventoryMgmt.InventoryMgmt.Repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookConditionRepository extends JpaRepository<BookCondition, Integer> {

    List<BookCondition> findByDescription(String description);

    List<BookCondition> findByPriceBetween(double min, double max);

    List<BookCondition> findByRanksGreaterThan(int rank);
}