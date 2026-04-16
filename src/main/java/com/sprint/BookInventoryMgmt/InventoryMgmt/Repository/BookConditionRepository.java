package com.sprint.BookInventoryMgmt.InventoryMgmt.Repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookConditionRepository extends JpaRepository<BookCondition, Integer> {

    List<BookCondition> findByDescription(String description);


    @Query("SELECT b FROM BookCondition b WHERE b.price BETWEEN :min AND :max")
    List<BookCondition> findByPriceRange(@Param("min") double min,
                                         @Param("max") double max);

    @Query("SELECT b FROM BookCondition b WHERE b.ranks > :rank")
    List<BookCondition> findByRankGreaterThan(@Param("rank") int rank);
}