package com.sprint.bookinventorymgmt.bookmgmt.repository;

import com.sprint.bookinventorymgmt.bookmgmt.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Optional: prevent duplicate category descriptions
    Optional<Category> findByCatDescription(String catDescription);

    boolean existsByCatDescription(String catDescription);
}