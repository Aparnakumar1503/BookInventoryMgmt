package com.sprint.BookInventoryMgmt.reviewmgmt.repository;

import com.sprint.BookInventoryMgmt.reviewmgmt.entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
}