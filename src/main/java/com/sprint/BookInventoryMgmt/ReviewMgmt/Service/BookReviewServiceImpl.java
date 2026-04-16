package com.sprint.BookInventoryMgmt.ReviewMgmt.Service;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.BookReview;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.BookReviewRepository;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService{
    @Autowired
    private BookReviewRepository repository;

    @Autowired
    private ReviewerRepository reviewerRepository;


    public BookReview addReview(BookReview review) {

        // ✅ Check reviewer exists (prevents 500 error)
        if (!reviewerRepository.existsById(review.getReviewerID())) {
            throw new RuntimeException("Reviewer not found with ID: " + review.getReviewerID());
        }

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
