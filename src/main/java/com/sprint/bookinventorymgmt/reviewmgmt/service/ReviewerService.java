package com.sprint.bookinventorymgmt.reviewmgmt.service;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.ReviewerResponseDTO;

import java.util.List;

public interface ReviewerService {

    ReviewerResponseDTO addReviewer(ReviewerRequestDTO reviewerDTO);

    ReviewerResponseDTO getReviewerById(int reviewerId);

    List<ReviewerResponseDTO> getAllReviewers();

    ReviewerResponseDTO updateReviewer(int reviewerId, ReviewerRequestDTO reviewerDTO);

    ReviewerResponseDTO deleteReviewer(int reviewerId); // ✅ changed
}