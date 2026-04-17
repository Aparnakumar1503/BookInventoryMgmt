package com.sprint.BookInventoryMgmt.reviewmgmt.service;

import com.sprint.BookInventoryMgmt.reviewmgmt.entity.BookReview;
import com.sprint.BookInventoryMgmt.reviewmgmt.exception.ReviewNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.BookReviewRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.ReviewerRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.BookReviewRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.BookReviewResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
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

    // ================= 8 POSITIVE =================

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
    void getAll_success() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        assertEquals(1, service.getAllReviews().size());
    }

    @Test
    void getByISBN_success() {
        when(reviewRepository.findByIsbn("1-111")).thenReturn(List.of(review));

        assertEquals(1, service.getReviewsByISBN("1-111").size());
    }

    @Test
    void getByReviewer_success() {
        when(reviewRepository.findByReviewerID(101)).thenReturn(List.of(review));

        assertEquals(1, service.getReviewsByReviewer(101).size());
    }

    @Test
    void delete_success() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        assertNotNull(service.deleteReview(1));
    }

    @Test
    void addReview_mapperCheck() {
        when(reviewerRepository.existsById(101)).thenReturn(true);
        when(reviewRepository.save(any())).thenReturn(review);

        BookReviewRequestDTO dto = new BookReviewRequestDTO("1-111", 101, 5, "Good");

        assertEquals(1, service.addReview(dto).getId());
    }

    @Test
    void getAll_empty() {
        when(reviewRepository.findAll()).thenReturn(List.of());

        assertTrue(service.getAllReviews().isEmpty());
    }

    @Test
    void save_called() {
        when(reviewerRepository.existsById(101)).thenReturn(true);
        when(reviewRepository.save(any())).thenReturn(review);

        BookReviewRequestDTO dto = new BookReviewRequestDTO("1-111", 101, 5, "Good");

        service.addReview(dto);

        verify(reviewRepository, times(1)).save(any());
    }

    // ================= 7 NEGATIVE =================

    @Test
    void addReview_invalidReviewer() {
        when(reviewerRepository.existsById(101)).thenReturn(false);

        BookReviewRequestDTO dto = new BookReviewRequestDTO("1-111", 101, 5, "Good");

        assertThrows(RuntimeException.class,
                () -> service.addReview(dto));
    }

    @Test
    void delete_notFound() {
        when(reviewRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class,
                () -> service.deleteReview(99));
    }

    @Test
    void getByISBN_empty() {
        when(reviewRepository.findByIsbn("x")).thenReturn(List.of());

        assertTrue(service.getReviewsByISBN("x").isEmpty());
    }

    @Test
    void getByReviewer_empty() {
        when(reviewRepository.findByReviewerID(999)).thenReturn(List.of());

        assertTrue(service.getReviewsByReviewer(999).isEmpty());
    }

    @Test
    void addReview_nullDTO() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addReview(null));
    }

    @Test
    void addReview_nullReviewerId() {
        BookReviewRequestDTO dto = new BookReviewRequestDTO();
        dto.setReviewerID(null);

        assertThrows(IllegalArgumentException.class,
                () -> service.addReview(dto));
    }

    @Test
    void repository_failure() {
        when(reviewRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> service.getAllReviews());
    }
}