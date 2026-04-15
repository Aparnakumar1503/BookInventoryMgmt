package com.sprint.BookInventoryMgmt.Inventory.Repository;

import com.sprint.BookInventoryMgmt.Inventory.Entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookConditionRepository extends JpaRepository<BookCondition, Integer> {

}
