package com.sprint.BookInventoryMgmt.InventoryMgmt.Repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookConditionRepository extends JpaRepository<BookCondition, Integer> {

}