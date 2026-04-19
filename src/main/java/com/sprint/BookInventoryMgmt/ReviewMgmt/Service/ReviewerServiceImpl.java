package com.sprint.BookInventoryMgmt.reviewmgmt.service;

import com.sprint.BookInventoryMgmt.reviewmgmt.entity.Reviewer;
import com.sprint.BookInventoryMgmt.reviewmgmt.repository.ReviewerRepository;
import com.sprint.BookInventoryMgmt.reviewmgmt.exceptions.ReviewerNotFoundException;
import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.ReviewerRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.ReviewerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService {

    @Autowired
    private ReviewerRepository repository;

    @Override
    public ReviewerResponseDTO addReviewer(ReviewerRequestDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Reviewer DTO cannot be null");
        }

        Reviewer reviewer = new Reviewer();
        reviewer.setName(dto.getName());
        reviewer.setEmployedBy(dto.getEmployedBy());

        Reviewer saved = repository.save(reviewer);

        return mapToDTO(saved);
    }

    @Override
    public ReviewerResponseDTO getReviewerById(int reviewerId) {

        Reviewer reviewer = repository.findById(reviewerId)
                .orElseThrow(() ->
                        new ReviewerNotFoundException("Reviewer not found with ID: " + reviewerId));

        return mapToDTO(reviewer);
    }

    @Override
    public List<ReviewerResponseDTO> getAllReviewers() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ReviewerResponseDTO updateReviewer(int reviewerId, ReviewerRequestDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Reviewer DTO cannot be null");
        }

        Reviewer existing = repository.findById(reviewerId)
                .orElseThrow(() ->
                        new ReviewerNotFoundException("Reviewer not found with ID: " + reviewerId));

        existing.setName(dto.getName());
        existing.setEmployedBy(dto.getEmployedBy());

        Reviewer updated = repository.save(existing);

        return mapToDTO(updated);
    }

    @Override
    public ReviewerResponseDTO deleteReviewer(int reviewerId) {

        Reviewer reviewer = repository.findById(reviewerId)
                .orElseThrow(() ->
                        new ReviewerNotFoundException("Reviewer not found with ID: " + reviewerId));

        repository.delete(reviewer);

        return mapToDTO(reviewer);
    }

    // 🔥 COMMON MAPPER METHOD
    private ReviewerResponseDTO mapToDTO(Reviewer reviewer) {

        if (reviewer == null) {
            throw new IllegalArgumentException("Reviewer cannot be null");
        }

        ReviewerResponseDTO dto = new ReviewerResponseDTO();
        dto.setReviewerID(reviewer.getReviewerID());
        dto.setName(reviewer.getName());
        dto.setEmployedBy(reviewer.getEmployedBy());

        return dto;
    }
}