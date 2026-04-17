package com.sprint.BookInventoryMgmt.InventoryMgmt.repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookConditionRepository extends JpaRepository<BookCondition, Integer> {

    List<BookCondition> findByPriceBetween(double min, double max);

    List<BookCondition> findByRanksGreaterThan(int rank);
}