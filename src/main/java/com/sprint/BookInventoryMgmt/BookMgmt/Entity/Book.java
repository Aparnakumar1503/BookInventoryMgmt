package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @Column(name = "ISBN", length = 13)
    @NotBlank(message = "ISBN is required")
    private String isbn;

    @Column(name = "Title", nullable = false, length = 70)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(name = "Description", length = 100)
    private String description;

    @Column(name = "Edition", length = 30)
    private String edition;

    // Many Books → One Category
    @ManyToOne
    @JoinColumn(name = "Category", referencedColumnName = "CatID")
    @NotNull(message = "Category is required")
    @JsonIgnoreProperties("books")
    private Category category;

    // Many Books → One Publisher
    @ManyToOne
    @JoinColumn(name = "PublisherID", referencedColumnName = "PublisherID")
    @NotNull(message = "Publisher is required")
    @JsonIgnoreProperties("books")
    private Publisher publisher;
}