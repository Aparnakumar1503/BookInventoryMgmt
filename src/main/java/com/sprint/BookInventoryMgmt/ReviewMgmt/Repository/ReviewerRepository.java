package com.sprint.BookInventoryMgmt.ReviewMgmt.Repository;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<reviewer, Integer> {
}