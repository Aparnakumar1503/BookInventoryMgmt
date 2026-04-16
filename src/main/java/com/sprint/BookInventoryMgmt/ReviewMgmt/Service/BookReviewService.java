package com.sprint.BookInventoryMgmt.ReviewMgmt.Service;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.BookReview;
import java.util.List;

public interface BookReviewService {

    BookReview addReview(BookReview review);

    List<BookReview> getReviewsByISBN(String isbn);

    List<BookReview> getAllReviews();

    void deleteReview(int id);
}