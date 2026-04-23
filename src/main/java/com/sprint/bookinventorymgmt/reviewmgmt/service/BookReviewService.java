package com.sprint.bookinventorymgmt.reviewmgmt.service;

import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.dto.BookReviewResponseDTO;

import java.util.List;

public interface BookReviewService {

    BookReviewResponseDTO addReview(BookReviewRequestDTO dto);

    List<BookReviewResponseDTO> getAllReviews();

    List<BookReviewResponseDTO> getReviewsByISBN(String isbn);

    List<BookReviewResponseDTO> getReviewsByReviewer(int reviewerId);

    List<BookReviewResponseDTO> getReviewsWithMaxRating();

    BookReviewResponseDTO deleteReview(int id);
}
