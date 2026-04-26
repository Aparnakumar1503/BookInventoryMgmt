package com.sprint.bookinventorymgmt.reviewmgmt.repository;

import com.sprint.bookinventorymgmt.reviewmgmt.entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
    List<Reviewer> findByEmployedByIgnoreCaseContaining(String company);
}
