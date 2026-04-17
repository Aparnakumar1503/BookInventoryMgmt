package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "isbn")
    private String isbn;

    private String title;
    private String description;
    private String edition;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
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