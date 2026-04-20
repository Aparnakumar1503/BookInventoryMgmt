package com.sprint.BookInventoryMgmt.reviewmgmt.service;
import com.sprint.BookInventoryMgmt.reviewmgmt.dto.ReviewerRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.dto.ReviewerResponseDTO;

import java.util.List;

public interface ReviewerService {

    ReviewerResponseDTO addReviewer(ReviewerRequestDTO reviewerDTO);

    ReviewerResponseDTO getReviewerById(int reviewerId);

    List<ReviewerResponseDTO> getAllReviewers();

    ReviewerResponseDTO updateReviewer(int reviewerId, ReviewerRequestDTO reviewerDTO);

    ReviewerResponseDTO deleteReviewer(int reviewerId); // ✅ changed
}