package com.sprint.bookinventorymgmt.bookmgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "isbn", nullable = false, unique = true)
    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Edition cannot be blank")
    private String edition;

    @ManyToOne
    @JoinColumn(name = "cat_id", nullable = false)
    @NotNull(message = "Category is required")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @NotNull(message = "Publisher is required")
    private Publisher publisher;

    public Book() {}

    public Book(String isbn, String title, String description, String edition,
                Category category, Publisher publisher) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.edition = edition;
        this.category = category;
        this.publisher = publisher;
    }

    // GETTERS & SETTERS
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
}