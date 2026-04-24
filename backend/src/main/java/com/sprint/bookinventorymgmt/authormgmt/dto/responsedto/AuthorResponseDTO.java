package com.sprint.bookinventorymgmt.authormgmt.dto.responsedto;

import lombok.Builder;


@Builder
public class AuthorResponseDTO {

    private Integer authorId; // returned in response after save
    private String firstName;
    private String lastName;
    private String photo;

    public AuthorResponseDTO() {}

    public AuthorResponseDTO(Integer authorId, String firstName, String lastName, String photo) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }

    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
}