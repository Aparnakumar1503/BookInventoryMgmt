package com.sprint.BookInventoryMgmt.InventoryMgmt.Repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookConditionRepository extends JpaRepository<BookCondition, Integer> {

    List<BookCondition> findByPriceBetween(Double min, Double max);

    List<BookCondition> findByRanksGreaterThan(Integer ranks);
}