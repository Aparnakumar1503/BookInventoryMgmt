package com.sprint.BookInventoryMgmt.bookmgmt.repository;

import com.sprint.BookInventoryMgmt.bookmgmt.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, String> {
}