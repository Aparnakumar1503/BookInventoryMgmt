package com.sprint.bookinventorymgmt.authormgmt.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
public class BookAuthorRequestDTO {

    @NotBlank(message = "ISBN is required")
    @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters")
    private String isbn;

    @NotNull(message = "Author ID is required")
    @Positive(message = "Author ID must be greater than 0")
    private Integer authorId;

    @Pattern(regexp = "^[YN]$", message = "Primary author must be either Y or N")
    private String primaryAuthor;

    public BookAuthorRequestDTO() {}

    public BookAuthorRequestDTO(String isbn, Integer authorId, String primaryAuthor) {
        this.isbn = isbn;
        this.authorId = authorId;
        this.primaryAuthor = primaryAuthor;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }
    public String getPrimaryAuthor() { return primaryAuthor; }
    public void setPrimaryAuthor(String primaryAuthor) { this.primaryAuthor = primaryAuthor; }
}
