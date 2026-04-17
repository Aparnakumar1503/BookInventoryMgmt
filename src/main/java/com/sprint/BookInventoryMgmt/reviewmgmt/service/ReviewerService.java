package com.sprint.BookInventoryMgmt.reviewmgmt.service;

import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.ReviewerRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.ReviewerResponseDTO;

import java.util.List;

public interface ReviewerService {

    ReviewerResponseDTO addReviewer(ReviewerRequestDTO reviewerDTO);

    ReviewerResponseDTO getReviewerById(int reviewerId);

    List<ReviewerResponseDTO> getAllReviewers();

    ReviewerResponseDTO updateReviewer(int reviewerId, ReviewerRequestDTO reviewerDTO);

    void deleteReviewer(int reviewerId);
}