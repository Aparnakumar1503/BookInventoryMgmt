package com.sprint.BookInventoryMgmt.authormgmt.dto.requestdto;

import lombok.Data;

@Data
public class BookAuthorRequestDTO {

    private String isbn;
    private Integer authorId;
    private String primaryAuthor;
}