package com.sprint.BookInventoryMgmt.reviewmgmt;

import com.sprint.BookInventoryMgmt.reviewmgmt.entity.BookReview;
import com.sprint.BookInventoryMgmt.reviewmgmt.entity.Reviewer;
import com.sprint.BookInventoryMgmt.reviewmgmt.exception.ReviewNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.exception.ReviewerNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.BookReviewRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.ReviewerRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.BookReviewRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.ReviewerRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.BookReviewResponseDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.ReviewerResponseDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.service.BookReviewServiceImpl;
import com.sprint.BookInventoryMgmt.reviewmgmt.service.ReviewerServiceImpl;
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
class ReviewManagementServiceTest {

    // ---------------- MOCKS ----------------
    @Mock
    private ReviewerRepository reviewerRepository;

    @Mock
    private BookReviewRepository reviewRepository;

    @InjectMocks
    private ReviewerServiceImpl reviewerService;

    @InjectMocks
    private BookReviewServiceImpl bookReviewService;

    private Reviewer reviewer;
    private BookReview review;

    @BeforeEach
    void setUp() {

        reviewer = new Reviewer();
        reviewer.setReviewerID(101);
        reviewer.setName("Swarna");
        reviewer.setEmployedBy("ABC Ltd");

        review = new BookReview();
        review.setId(1);
        review.setIsbn("1-111");
        review.setReviewerID(101);
        review.setRating(5);
        review.setComments("Good");
    }

    // ======================================================
    // ✅ 8 POSITIVE TEST CASES
    // ======================================================

    @Test
    void addReviewer_success() {
        when(reviewerRepository.save(any())).thenReturn(reviewer);

        ReviewerRequestDTO dto = new ReviewerRequestDTO("Swarna", "ABC Ltd");

        ReviewerResponseDTO result = reviewerService.addReviewer(dto);

        assertEquals("Swarna", result.getName());
    }

    @Test
    void getReviewer_success() {
        when(reviewerRepository.findById(101)).thenReturn(Optional.of(reviewer));

        ReviewerResponseDTO result = reviewerService.getReviewerById(101);

        assertEquals(101, result.getReviewerID());
    }

    @Test
    void updateReviewer_success() {
        when(reviewerRepository.findById(101)).thenReturn(Optional.of(reviewer));
        when(reviewerRepository.save(any())).thenReturn(reviewer);

        ReviewerRequestDTO dto = new ReviewerRequestDTO("Updated", "XYZ");

        ReviewerResponseDTO result = reviewerService.updateReviewer(101, dto);

        assertEquals("Updated", result.getName());
    }

    @Test
    void addReview_success() {
        when(reviewerRepository.existsById(101)).thenReturn(true);
        when(reviewRepository.save(any())).thenReturn(review);

        BookReviewRequestDTO dto = new BookReviewRequestDTO("1-111", 101, 5, "Good");

        BookReviewResponseDTO result = bookReviewService.addReview(dto);

        assertEquals(101, result.getReviewerID());
    }

    @Test
    void getAllReviews_success() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        assertEquals(1, bookReviewService.getAllReviews().size());
    }

    @Test
    void getReviewsByISBN_success() {
        when(reviewRepository.findByIsbn("1-111")).thenReturn(List.of(review));

        assertEquals(1, bookReviewService.getReviewsByISBN("1-111").size());
    }

    @Test
    void deleteReview_success() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        assertNotNull(bookReviewService.deleteReview(1));
    }

    @Test
    void addReviewer_mapperCheck() {
        when(reviewerRepository.save(any())).thenReturn(reviewer);

        ReviewerRequestDTO dto = new ReviewerRequestDTO("Test", "Org");

        assertEquals(101, reviewerService.addReviewer(dto).getReviewerID());
    }

    // ======================================================
    // ❌ 7 NEGATIVE TEST CASES
    // ======================================================

    @Test
    void reviewer_notFound() {
        when(reviewerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ReviewerNotFoundException.class,
                () -> reviewerService.getReviewerById(999));
    }

    @Test
    void updateReviewer_notFound() {
        when(reviewerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ReviewerNotFoundException.class,
                () -> reviewerService.updateReviewer(999, new ReviewerRequestDTO()));
    }

    @Test
    void deleteReviewer_notFound() {
        when(reviewerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ReviewerNotFoundException.class,
                () -> reviewerService.deleteReviewer(999));
    }

    @Test
    void addReview_invalidReviewer() {
        when(reviewerRepository.existsById(101)).thenReturn(false);

        BookReviewRequestDTO dto = new BookReviewRequestDTO("1-111", 101, 5, "Good");

        assertThrows(RuntimeException.class,
                () -> bookReviewService.addReview(dto));
    }

    @Test
    void deleteReview_notFound() {
        when(reviewRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class,
                () -> bookReviewService.deleteReview(99));
    }

    @Test
    void addReviewer_nullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> reviewerService.addReviewer(null));
    }

    @Test
    void addReview_nullDTO() {
        assertThrows(IllegalArgumentException.class,
                () -> bookReviewService.addReview(null));
    }
}