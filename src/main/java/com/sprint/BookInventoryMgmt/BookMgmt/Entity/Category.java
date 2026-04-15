package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @Column(name = "CatID")
    private Integer catId;

    @Column(name = "CatDescription", length = 24)
    @NotBlank(message = "Category description is required")
    private String catDescription;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Book> books;
}