package com.sprint.BookInventoryMgmt.reviewmgmt.service;

import com.sprint.BookInventoryMgmt.reviewmgmt.dto.ReviewerRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.dto.ReviewerResponseDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.entity.Reviewer;
import com.sprint.BookInventoryMgmt.reviewmgmt.exceptions.ReviewerNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.ReviewerRepository;

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
class ReviewerServiceTest {

    @Mock
    private ReviewerRepository repository;

    @InjectMocks
    private ReviewerServiceImpl service;

    private Reviewer reviewer;

    @BeforeEach
    void setUp() {
        reviewer = new Reviewer();
        reviewer.setReviewerID(101);
        reviewer.setName("name");
        reviewer.setEmployedBy("ABC Ltd");
    }

    // ================= 4 POSITIVE TEST CASES =================

    @Test
    void addReviewer_success() {
        when(repository.save(any())).thenReturn(reviewer);

        ReviewerRequestDTO dto = new ReviewerRequestDTO();
        dto.setName("name");
        dto.setEmployedBy("ABC Ltd");

        ReviewerResponseDTO result = service.addReviewer(dto);

        assertNotNull(result);
        assertEquals("name", result.getName());
    }

    @Test
    void getReviewerById_success() {
        when(repository.findById(101)).thenReturn(Optional.of(reviewer));

        ReviewerResponseDTO result = service.getReviewerById(101);

        assertEquals(101, result.getReviewerID());
    }

    @Test
    void getAllReviewers_success() {
        when(repository.findAll()).thenReturn(List.of(reviewer));

        List<ReviewerResponseDTO> result = service.getAllReviewers();

        assertEquals(1, result.size());
    }

    @Test
    void updateReviewer_success() {
        when(repository.findById(101)).thenReturn(Optional.of(reviewer));
        when(repository.save(any())).thenReturn(reviewer);

        ReviewerRequestDTO dto = new ReviewerRequestDTO();
        dto.setName("Updated");
        dto.setEmployedBy("XYZ");

        ReviewerResponseDTO result = service.updateReviewer(101, dto);

        assertEquals("Updated", result.getName());
        assertEquals("XYZ", result.getEmployedBy());
    }

    // ================= 4 NEGATIVE TEST CASES =================

    @Test
    void getReviewerById_notFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ReviewerNotFoundException.class,
                () -> service.getReviewerById(999));
    }

    @Test
    void updateReviewer_notFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ReviewerNotFoundException.class,
                () -> service.updateReviewer(999, new ReviewerRequestDTO()));
    }

    @Test
    void deleteReviewer_notFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ReviewerNotFoundException.class,
                () -> service.deleteReviewer(999));
    }

    @Test
    void addReviewer_nullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addReviewer(null));
    }
}