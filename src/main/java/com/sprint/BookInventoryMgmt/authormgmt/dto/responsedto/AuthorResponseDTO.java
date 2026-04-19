package com.sprint.BookInventoryMgmt.authormgmt.dto.responsedto;

import lombok.Data;

@Data
public class AuthorResponseDTO {

    private Integer authorId;
    private String firstName;
    private String lastName;
    private String photo;
}