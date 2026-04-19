package com.sprint.BookInventoryMgmt.authormgmt.dto.responsedto;

import lombok.Builder;
import lombok.Data;

@Builder
public class BookAuthorResponseDTO {

    private String isbn;
    private Integer authorId;
    private String primaryAuthor;
    private String firstName;
    private String lastName;
    private String bookTitle;

    public BookAuthorResponseDTO() {}

    public BookAuthorResponseDTO(String isbn, Integer authorId, String primaryAuthor,
                                 String firstName, String lastName, String bookTitle) {
        this.isbn = isbn;
        this.authorId = authorId;
        this.primaryAuthor = primaryAuthor;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookTitle = bookTitle;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }
    public String getPrimaryAuthor() { return primaryAuthor; }
    public void setPrimaryAuthor(String primaryAuthor) { this.primaryAuthor = primaryAuthor; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
}