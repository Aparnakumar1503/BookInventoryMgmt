package com.sprint.BookInventoryMgmt.reviewmgmt.service;

import com.sprint.BookInventoryMgmt.reviewmgmt.entity.BookReview;
import com.sprint.BookInventoryMgmt.reviewmgmt.exception.ReviewNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.BookReviewRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.ReviewerRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.exception.ReviewerNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.BookReviewRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.BookReviewResponseDTO;
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
    public BookReviewResponseDTO addReview(BookReviewRequestDTO dto) {

        if (!reviewerRepository.existsById(dto.getReviewerID())) {
            throw new RuntimeException("Reviewer not found");
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
        return reviewRepository.findAll().stream().map(r -> {
            BookReviewResponseDTO dto = new BookReviewResponseDTO();
            dto.setId(r.getId());
            dto.setIsbn(r.getIsbn());
            dto.setReviewerID(r.getReviewerID());
            dto.setRating(r.getRating());
            dto.setComments(r.getComments());
            return dto;
        }).toList();
    }

    @Override
    public List<BookReviewResponseDTO> getReviewsByISBN(String isbn) {
        return reviewRepository.findByIsbn(isbn).stream().map(r -> {
            BookReviewResponseDTO dto = new BookReviewResponseDTO();
            dto.setId(r.getId());
            dto.setIsbn(r.getIsbn());
            dto.setReviewerID(r.getReviewerID());
            dto.setRating(r.getRating());
            dto.setComments(r.getComments());
            return dto;
        }).toList();
    }

    @Override
    public List<BookReviewResponseDTO> getReviewsByReviewer(int reviewerId) {
        return reviewRepository.findByReviewerID(reviewerId).stream().map(r -> {
            BookReviewResponseDTO dto = new BookReviewResponseDTO();
            dto.setId(r.getId());
            dto.setIsbn(r.getIsbn());
            dto.setReviewerID(r.getReviewerID());
            dto.setRating(r.getRating());
            dto.setComments(r.getComments());
            return dto;
        }).toList();
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