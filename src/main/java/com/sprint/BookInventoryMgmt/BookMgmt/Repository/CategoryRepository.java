package com.sprint.BookInventoryMgmt.BookMgmt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}