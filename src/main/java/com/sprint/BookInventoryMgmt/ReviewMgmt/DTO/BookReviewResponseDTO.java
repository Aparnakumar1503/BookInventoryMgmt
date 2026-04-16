package com.sprint.BookInventoryMgmt.ReviewMgmt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewResponseDTO {

    private int reviewId;
    private String isbn;
    private Integer reviewerID;
    private String reviewText;
    private int rating;

}