package com.sprint.BookInventoryMgmt.authormgmt.dto.responsedto;

import lombok.Data;

@Data
public class BookAuthorResponseDTO {

    private String isbn;
    private Integer authorId;
    private String primaryAuthor;

    // Optional display fields
    private String authorName;
    private String bookTitle;
}