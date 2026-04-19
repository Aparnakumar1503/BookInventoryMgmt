package com.sprint.bookinventorymgmt.reviewmgmt.repository;

import com.sprint.bookinventorymgmt.reviewmgmt.entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
}