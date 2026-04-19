package com.sprint.BookInventoryMgmt.reviewmgmt.service;

import com.sprint.BookInventoryMgmt.reviewmgmt.entity.BookReview;
import com.sprint.BookInventoryMgmt.reviewmgmt.exceptions.ReviewNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.BookReviewRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.ReviewerRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.BookReviewRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.BookReviewResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookReviewServiceTest {

    @Mock
    private BookReviewRepository reviewRepository;

    @Mock
    private ReviewerRepository reviewerRepository;

    @InjectMocks
    private BookReviewServiceImpl service;

    private BookReview review;

    @BeforeEach
    void setUp() {
        review = new BookReview();
        review.setId(1);
        review.setIsbn("1-111");
        review.setReviewerID(101);
        review.setRating(5);
        review.setComments("Good");
    }

    // ================= 4 POSITIVE TEST CASES =================

    @Test
    void addReview_success() {
        when(reviewerRepository.existsById(101)).thenReturn(true);
        when(reviewRepository.save(any())).thenReturn(review);

        BookReviewRequestDTO dto = new BookReviewRequestDTO("1-111", 101, 5, "Good");

        BookReviewResponseDTO result = service.addReview(dto);

        assertNotNull(result);
        assertEquals(101, result.getReviewerID());
    }

    @Test
    void getAllReviews_success() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        assertEquals(1, service.getAllReviews().size());
    }

    @Test
    void getReviewsByISBN_success() {
        when(reviewRepository.findByIsbn("1-111")).thenReturn(List.of(review));

        assertEquals(1, service.getReviewsByISBN("1-111").size());
    }

    @Test
    void deleteReview_success() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        assertNotNull(service.deleteReview(1));
    }

    // ================= 3 NEGATIVE TEST CASES =================

    @Test
    void addReview_invalidReviewer() {
        when(reviewerRepository.existsById(101)).thenReturn(false);

        BookReviewRequestDTO dto = new BookReviewRequestDTO("1-111", 101, 5, "Good");

        assertThrows(RuntimeException.class,
                () -> service.addReview(dto));
    }

    @Test
    void deleteReview_notFound() {
        when(reviewRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class,
                () -> service.deleteReview(99));
    }

    @Test
    void addReview_nullDTO() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addReview(null));
    }
}