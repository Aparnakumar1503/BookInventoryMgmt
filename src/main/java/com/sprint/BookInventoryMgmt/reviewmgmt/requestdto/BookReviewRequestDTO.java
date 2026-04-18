package com.sprint.BookInventoryMgmt.reviewmgmt.requestdto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookReviewRequestDTO {

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Reviewer ID is required")
    private Integer reviewerID;

    @Min(1)
    @Max(10)
    private Integer rating;

    private String comments;

    public BookReviewRequestDTO() {}

    public BookReviewRequestDTO(String isbn, Integer reviewerID, Integer rating, String comments) {
        this.isbn = isbn;
        this.reviewerID = reviewerID;
        this.rating = rating;
        this.comments = comments;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getReviewerID() { return reviewerID; }
    public void setReviewerID(Integer reviewerID) { this.reviewerID = reviewerID; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}