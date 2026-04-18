package com.sprint.bookinventorymgmt.bookmgmt.repository;

import com.sprint.bookinventorymgmt.bookmgmt.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, String> {

    // Find by state name
    Optional<State> findByStateName(String stateName);

    boolean existsByStateName(String stateName);
}