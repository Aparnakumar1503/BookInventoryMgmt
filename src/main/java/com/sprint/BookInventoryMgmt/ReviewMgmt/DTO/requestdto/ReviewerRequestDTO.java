package com.sprint.BookInventoryMgmt.ReviewMgmt.DTO.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String employedBy;

}