package com.sprint.BookInventoryMgmt.BookMgmt.Repository;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}