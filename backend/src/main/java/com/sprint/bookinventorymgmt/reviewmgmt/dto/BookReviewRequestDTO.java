package com.sprint.bookinventorymgmt.reviewmgmt.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookReviewRequestDTO {

    @NotBlank(message = "ISBN is required")
    @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters")
    private String isbn;

    @NotNull(message = "Reviewer ID is required")
    private Integer reviewerID;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must not exceed 10")
    private Integer rating;

    @Size(max = 500, message = "Comments cannot exceed 500 characters")
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
