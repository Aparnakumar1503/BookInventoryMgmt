package com.sprint.bookinventorymgmt.reviewmgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "book_review")
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "ISBN cannot be empty")
    @Column(name = "isbn", nullable = false)
    private String isbn;

    @NotNull(message = "Reviewer ID is required")
    @Column(name = "reviewer_id", nullable = false)
    private Integer reviewerID;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be >= 1")
    @Max(value = 10, message = "Rating must be <= 10")
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comments")
    private String comments;

    public BookReview() {
    }

    public BookReview(Integer id, String isbn, Integer reviewerID, Integer rating, String comments) {
        this.id = id;
        this.isbn = isbn;
        this.reviewerID = reviewerID;
        this.rating = rating;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(Integer reviewerID) {
        this.reviewerID = reviewerID;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
