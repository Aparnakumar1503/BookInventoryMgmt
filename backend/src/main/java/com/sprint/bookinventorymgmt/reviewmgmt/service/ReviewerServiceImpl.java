package com.sprint.bookinventorymgmt.reviewmgmt.service;

import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerResponseDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.entity.Reviewer;
import com.sprint.bookinventorymgmt.reviewmgmt.repository.ReviewerRepository;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewerNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService {
    @Autowired
    private  ReviewerRepository repository;

    public ReviewerServiceImpl(ReviewerRepository repository) {
        this.repository = repository;
    }

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
        List<Reviewer> reviewers = repository.findAll();
        List<ReviewerResponseDTO> response = new ArrayList<>();

        for (Reviewer reviewer : reviewers) {
            response.add(mapToDTO(reviewer));
        }

        return response;
    }

    @Override
    public List<ReviewerResponseDTO> getReviewersByCompany(String company) {
        List<Reviewer> reviewers = repository.findByEmployedByIgnoreCaseContaining(company);
        List<ReviewerResponseDTO> response = new ArrayList<>();

        for (Reviewer reviewer : reviewers) {
            response.add(mapToDTO(reviewer));
        }

        return response;
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
