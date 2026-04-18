package com.sprint.BookInventoryMgmt.AuthorMgmt.dto.responsedto;

import lombok.Data;

@Data
public class AuthorResponseDTO {

    private Integer authorId;
    private String firstName;
    private String lastName;
    private String photo;
}