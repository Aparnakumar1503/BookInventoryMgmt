package com.sprint.bookinventorymgmt.reviewmgmt.service;

import com.sprint.bookinventorymgmt.reviewmgmt.entity.BookReview;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.InvalidRatingException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewNotFoundException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewerNotFoundException;
import com.sprint.bookinventorymgmt.reviewmgmt.repository.BookReviewRepository;
import com.sprint.bookinventorymgmt.reviewmgmt.repository.ReviewerRepository;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {
    @Autowired
    private  BookReviewRepository reviewRepository;
    @Autowired
    private  ReviewerRepository reviewerRepository;

    public BookReviewServiceImpl(BookReviewRepository reviewRepository, ReviewerRepository reviewerRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewerRepository = reviewerRepository;
    }

    @Override
    public BookReviewResponseDTO addReview(BookReviewRequestDTO dto) {

        if (dto == null || dto.getReviewerID() == null) {
            throw new InvalidRatingException("Reviewer ID cannot be null");
        }

        if (dto.getRating() == null || dto.getRating() < 1 || dto.getRating() > 10) {
            throw new InvalidRatingException("Rating must be between 1 and 10");
        }

        if (!reviewerRepository.existsById(dto.getReviewerID())) {
            throw new ReviewerNotFoundException("Reviewer not found with ID: " + dto.getReviewerID());
        }

        BookReview review = new BookReview();
        review.setIsbn(dto.getIsbn());
        review.setReviewerID(dto.getReviewerID());
        review.setRating(dto.getRating());
        review.setComments(dto.getComments());

        BookReview saved = reviewRepository.save(review);

        BookReviewResponseDTO res = new BookReviewResponseDTO();
        res.setId(saved.getId());
        res.setIsbn(saved.getIsbn());
        res.setReviewerID(saved.getReviewerID());
        res.setRating(saved.getRating());
        res.setComments(saved.getComments());

        return res;
    }

    @Override
    public List<BookReviewResponseDTO> getAllReviews() {
        List<BookReview> reviews = reviewRepository.findAll();
        List<BookReviewResponseDTO> response = new ArrayList<>();

        for (BookReview r : reviews) {
            BookReviewResponseDTO dto = new BookReviewResponseDTO();
            dto.setId(r.getId());
            dto.setIsbn(r.getIsbn());
            dto.setReviewerID(r.getReviewerID());
            dto.setRating(r.getRating());
            dto.setComments(r.getComments());
            response.add(dto);
        }

        return response;
    }

    @Override
    public List<BookReviewResponseDTO> getReviewsByISBN(String isbn) {
        List<BookReview> reviews = reviewRepository.findByIsbn(isbn);
        List<BookReviewResponseDTO> response = new ArrayList<>();

        for (BookReview r : reviews) {
            BookReviewResponseDTO dto = new BookReviewResponseDTO();
            dto.setId(r.getId());
            dto.setIsbn(r.getIsbn());
            dto.setReviewerID(r.getReviewerID());
            dto.setRating(r.getRating());
            dto.setComments(r.getComments());
            response.add(dto);
        }

        return response;
    }

    @Override
    public List<BookReviewResponseDTO> getReviewsByReviewer(int reviewerId) {
        List<BookReview> reviews = reviewRepository.findByReviewerID(reviewerId);
        List<BookReviewResponseDTO> response = new ArrayList<>();

        for (BookReview r : reviews) {
            BookReviewResponseDTO dto = new BookReviewResponseDTO();
            dto.setId(r.getId());
            dto.setIsbn(r.getIsbn());
            dto.setReviewerID(r.getReviewerID());
            dto.setRating(r.getRating());
            dto.setComments(r.getComments());
            response.add(dto);
        }

        return response;
    }

    @Override
    public List<BookReviewResponseDTO> getReviewsWithMaxRating() {
        List<BookReview> reviews = reviewRepository.findByMaxRating();
        List<BookReviewResponseDTO> response = new ArrayList<>();

        for (BookReview r : reviews) {
            BookReviewResponseDTO dto = new BookReviewResponseDTO();
            dto.setId(r.getId());
            dto.setIsbn(r.getIsbn());
            dto.setReviewerID(r.getReviewerID());
            dto.setRating(r.getRating());
            dto.setComments(r.getComments());
            response.add(dto);
        }

        return response;
    }

    @Override
    public BookReviewResponseDTO deleteReview(int id) {

        BookReview review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ReviewNotFoundException("Review not found with ID: " + id));

        reviewRepository.delete(review);

        BookReviewResponseDTO dto = new BookReviewResponseDTO();
        dto.setId(review.getId());
        dto.setIsbn(review.getIsbn());
        dto.setReviewerID(review.getReviewerID());
        dto.setRating(review.getRating());
        dto.setComments(review.getComments());

        return dto;
    }
}
