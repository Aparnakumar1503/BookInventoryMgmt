package com.sprint.bookinventorymgmt.bookmgmt.repository;

import com.sprint.bookinventorymgmt.bookmgmt.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

    // Find by name
    Optional<Publisher> findByName(String name);

    // Filter by state
    List<Publisher> findByStateStateCode(String stateCode);

    // Prevent duplicates
    boolean existsByName(String name);
}