package com.sprint.BookInventoryMgmt.ReviewMgmt.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookreview")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class bookreview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "ISBN cannot be empty")
    @Column(name = "ISBN")
    private String isbn;

    @NotNull(message = "Reviewer ID is required")
    @Column(name = "ReviewerID")
    private Integer reviewerID;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be >= 1")
    @Max(value = 10, message = "Rating must be <= 10")
    private Integer rating;

    private String comments;
}