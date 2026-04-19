package com.sprint.BookInventoryMgmt.bookmgmt.repository;


import com.sprint.BookInventoryMgmt.bookmgmt.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}