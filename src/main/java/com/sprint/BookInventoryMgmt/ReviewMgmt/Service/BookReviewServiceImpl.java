package com.sprint.BookInventoryMgmt.ReviewMgmt.Service;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.BookReview;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Exception.ReviewNotFoundException;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.BookReviewRepository;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.ReviewerRepository;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Exception.ReviewerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    @Autowired
    private BookReviewRepository reviewRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Override
    public BookReview addReview(BookReview review) {

        if (!reviewerRepository.existsById(review.getReviewerID())) {
            throw new ReviewerNotFoundException("Reviewer not found with ID: " + review.getReviewerID());
        }

        return reviewRepository.save(review);
    }

    @Override
    public List<BookReview> getReviewsByISBN(String isbn) {
        return reviewRepository.findByIsbn(isbn);
    }

    @Override
    public List<BookReview> getReviewsByReviewer(int reviewerId) {
        if (!reviewerRepository.existsById(reviewerId)) {
            throw new ReviewerNotFoundException("Reviewer not found with ID: " + reviewerId);
        }
        return reviewRepository.findByReviewerID(reviewerId);
    }

    @Override
    public List<BookReview> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void deleteReview(int id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with ID: " + id);
        }
        reviewRepository.deleteById(id);
    }
}