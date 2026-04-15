package com.sprint.BookInventoryMgmt.ReviewMgmt.Service;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.BookReview;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.BookReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BookReviewServiceImpl {
    @Autowired
    private BookReviewRepository repository;

    public BookReview addReview(BookReview review) {
        return repository.save(review);
    }

    public List<BookReview> getReviewsByISBN(String isbn) {
        return repository.findByIsbn(isbn);
    }

    public List<BookReview> getAllReviews() {
        return repository.findAll();
    }

    public void deleteReview(int id) {
        repository.deleteById(id);
    }
}
