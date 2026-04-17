package com.sprint.BookInventoryMgmt.AuthorMgmt.dto.requestdto;

import lombok.Data;

@Data
public class BookAuthorRequestDTO {

    private String isbn;
    private Integer authorId;
    private String primaryAuthor;
}