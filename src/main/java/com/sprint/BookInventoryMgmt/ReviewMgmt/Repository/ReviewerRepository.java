package com.sprint.BookInventoryMgmt.ReviewMgmt.Repository;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
}