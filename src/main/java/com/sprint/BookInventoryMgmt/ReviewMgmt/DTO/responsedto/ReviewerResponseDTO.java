package com.sprint.BookInventoryMgmt.ReviewMgmt.DTO.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerResponseDTO {

    private int reviewerId;
    private String name;
    private String employedBy;

}