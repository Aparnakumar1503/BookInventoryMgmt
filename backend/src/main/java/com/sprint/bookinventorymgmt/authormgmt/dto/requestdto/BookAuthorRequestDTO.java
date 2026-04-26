package com.sprint.bookinventorymgmt.authormgmt.dto.requestdto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
public class BookAuthorRequestDTO {

    private String isbn;

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
