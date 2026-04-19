package com.sprint.bookinventorymgmt.reviewmgmt.service;

import com.sprint.bookinventorymgmt.reviewmgmt.requestdto.BookReviewRequestDTO;
import com.sprint.bookinventorymgmt.reviewmgmt.responsedto.BookReviewResponseDTO;

import java.util.List;

public interface BookReviewService {

    BookReviewResponseDTO addReview(BookReviewRequestDTO dto);

    List<BookReviewResponseDTO> getAllReviews();

    List<BookReviewResponseDTO> getReviewsByISBN(String isbn);

    List<BookReviewResponseDTO> getReviewsByReviewer(int reviewerId);

    BookReviewResponseDTO deleteReview(int id);}