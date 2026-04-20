package com.sprint.BookInventoryMgmt.reviewmgmt.dto;


public class BookReviewResponseDTO {

    private Integer id;
    private String isbn;
    private Integer reviewerID;
    private Integer rating;
    private String comments;

    public BookReviewResponseDTO() {}

    public BookReviewResponseDTO(Integer id, String isbn, Integer reviewerID,
                                 Integer rating, String comments) {
        this.id = id;
        this.isbn = isbn;
        this.reviewerID = reviewerID;
        this.rating = rating;
        this.comments = comments;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getReviewerID() { return reviewerID; }
    public void setReviewerID(Integer reviewerID) { this.reviewerID = reviewerID; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

}