package com.sprint.bookinventorymgmt.reviewmgmt.repository;

import com.sprint.bookinventorymgmt.reviewmgmt.entity.BookReview;
import com.sprint.bookinventorymgmt.reviewmgmt.entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {
    List<BookReview> findByIsbn(String isbn);
    List<BookReview> findByReviewerID(Integer reviewerID);

    @Query("SELECT br FROM BookReview br WHERE br.rating = (SELECT MAX(b.rating) FROM BookReview b)")
    List<BookReview> findByMaxRating();
    @Query("SELECT r FROM Reviewer r WHERE r.employedBy = :company")
    List<Reviewer> findReviewersByCompany(@Param("company") String company);
}
