package com.sprint.BookInventoryMgmt.ReviewMgmt.Repository;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.bookreview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<bookreview, Integer> {
    List<bookreview> findByIsbn(String isbn);
    List<bookreview> findByReviewerID(Integer reviewerID);
}