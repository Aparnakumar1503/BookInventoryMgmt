package com.sprint.BookInventoryMgmt.ReviewMgmt.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Reviewer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reviewer {

    @Id
    @NotNull(message = "Reviewer ID cannot be null")
    private Integer reviewerID;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "EmployedBy")
    private String employedBy;
}