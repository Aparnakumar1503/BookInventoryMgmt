package com.sprint.bookinventorymgmt.authormgmt.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
public class AuthorRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z '-]*$", message = "First name must contain only letters, spaces, apostrophes, or hyphens")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z '-]*$", message = "Last name must contain only letters, spaces, apostrophes, or hyphens")
    private String lastName;

    @Size(max = 255, message = "Photo URL cannot exceed 255 characters")
    private String photo; // optional

    public AuthorRequestDTO() {}

    public AuthorRequestDTO(String firstName, String lastName, String photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
}
