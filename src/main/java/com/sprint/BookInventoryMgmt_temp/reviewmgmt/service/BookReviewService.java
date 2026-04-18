package com.sprint.BookInventoryMgmt.reviewmgmt.service;

import com.sprint.BookInventoryMgmt.reviewmgmt.requestdto.BookReviewRequestDTO;
import com.sprint.BookInventoryMgmt.reviewmgmt.responsedto.BookReviewResponseDTO;

import java.util.List;

public interface BookReviewService {

    BookReviewResponseDTO addReview(BookReviewRequestDTO dto);

    List<BookReviewResponseDTO> getAllReviews();

    List<BookReviewResponseDTO> getReviewsByISBN(String isbn);

    List<BookReviewResponseDTO> getReviewsByReviewer(int reviewerId);

    BookReviewResponseDTO deleteReview(int id);}