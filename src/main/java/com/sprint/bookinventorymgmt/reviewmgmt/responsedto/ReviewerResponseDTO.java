package com.sprint.bookinventorymgmt.reviewmgmt.responsedto;

public class ReviewerResponseDTO {

    private Integer reviewerID;
    private String name;
    private String employedBy;

    public ReviewerResponseDTO() {
    }

    public ReviewerResponseDTO(Integer reviewerID, String name, String employedBy) {
        this.reviewerID = reviewerID;
        this.name = name;
        this.employedBy = employedBy;
    }

    public Integer getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(Integer reviewerID) {
        this.reviewerID = reviewerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployedBy() {
        return employedBy;
    }

    public void setEmployedBy(String employedBy) {
        this.employedBy = employedBy;
    }

}
