package com.sprint.BookInventoryMgmt.ReviewMgmt.Repository;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {
    List<BookReview> findByIsbn(String isbn);
    List<BookReview> findByReviewerID(Integer reviewerID);
}