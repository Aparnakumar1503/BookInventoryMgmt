package com.sprint.BookInventoryMgmt.BookMgmt.Repository;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, String> {
}