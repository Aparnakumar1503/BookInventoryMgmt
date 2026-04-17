package com.sprint.BookInventoryMgmt.reviewmgmt.service;

import com.sprint.BookInventoryMgmt.reviewmgmt.entity.Reviewer;
import com.sprint.BookInventoryMgmt.reviewmgmt.exception.ReviewerNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.ReviewerRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.ReviewerRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.ReviewerResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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

    // ================= 8 POSITIVE =================

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

    @Test
    void deleteReviewer_success() {
        when(repository.findById(101)).thenReturn(Optional.of(reviewer));

        ReviewerResponseDTO result = service.deleteReviewer(101);

        assertNotNull(result);
        verify(repository).delete(reviewer);
    }

    @Test
    void getAllReviewers_emptyList() {
        when(repository.findAll()).thenReturn(List.of());

        assertTrue(service.getAllReviewers().isEmpty());
    }

    @Test
    void addReviewer_mapperCheck() {
        when(repository.save(any())).thenReturn(reviewer);

        ReviewerRequestDTO dto = new ReviewerRequestDTO();
        dto.setName("Test");
        dto.setEmployedBy("Org");

        ReviewerResponseDTO result = service.addReviewer(dto);

        assertEquals(101, result.getReviewerID());
    }

    @Test
    void updateReviewer_saveCalled() {
        when(repository.findById(101)).thenReturn(Optional.of(reviewer));
        when(repository.save(any())).thenReturn(reviewer);

        service.updateReviewer(101, new ReviewerRequestDTO());

        verify(repository, times(1)).save(any());
    }

    // ================= 7 NEGATIVE =================

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

    @Test
    void getReviewerById_invalidId_zero() {
        when(repository.findById(0)).thenReturn(Optional.empty());

        assertThrows(ReviewerNotFoundException.class,
                () -> service.getReviewerById(0));
    }

    @Test
    void updateReviewer_invalidData() {

        Reviewer existing = new Reviewer();
        existing.setReviewerID(101);
        existing.setName("name");
        existing.setEmployedBy("ABC Ltd");

        when(repository.findById(101)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        ReviewerRequestDTO dto = new ReviewerRequestDTO();
        dto.setName(null);
        dto.setEmployedBy(null);

        ReviewerResponseDTO result = service.updateReviewer(101, dto);

        assertNotNull(result);
        assertEquals(101, result.getReviewerID());
    }

    @Test
    void deleteReviewer_invalidId_negative() {
        when(repository.findById(-1)).thenReturn(Optional.empty());

        assertThrows(ReviewerNotFoundException.class,
                () -> service.deleteReviewer(-1));
    }
}