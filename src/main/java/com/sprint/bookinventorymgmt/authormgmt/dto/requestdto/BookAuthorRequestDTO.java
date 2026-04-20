package com.sprint.BookInventoryMgmt.authormgmt.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
public class BookAuthorRequestDTO {

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Author ID is required")
    private Integer authorId;

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