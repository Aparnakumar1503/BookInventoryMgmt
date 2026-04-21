package com.sprint.bookinventorymgmt.authormgmt.entity;

import com.sprint.bookinventorymgmt.bookmgmt.entity.Book;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookauthor")
@IdClass(BookAuthorId.class)

public class BookAuthor {

	@Id
	@Column(name = "ISBN")
	private String isbn;

	@Id
	@Column(name = "AuthorID")
	private Integer authorId;

	// 🔥 RELATIONSHIP TO BOOK
	@ManyToOne
	@JoinColumn(name = "ISBN", insertable = false, updatable = false)
	private Book book;

	// 🔥 RELATIONSHIP TO AUTHOR
	@ManyToOne
	@JoinColumn(name = "AuthorID", insertable = false, updatable = false)
	private Author author;

	@Column(name = "PrimaryAuthor")
	private String primaryAuthor;

	public BookAuthor() {}

	// All-arg constructor
	public BookAuthor(String isbn, Integer authorId, Book book, Author author, String primaryAuthor) {
		this.isbn = isbn;
		this.authorId = authorId;
		this.book = book;
		this.author = author;
		this.primaryAuthor = primaryAuthor;
	}

	// Getters
	public String getIsbn() { return isbn; }
	public Integer getAuthorId() { return authorId; }
	public Book getBook() { return book; }
	public Author getAuthor() { return author; }
	public String getPrimaryAuthor() { return primaryAuthor; }

	// Setters
	public void setIsbn(String isbn) { this.isbn = isbn; }
	public void setAuthorId(Integer authorId) { this.authorId = authorId; }
	public void setBook(Book book) { this.book = book; }
	public void setAuthor(Author author) { this.author = author; }
	public void setPrimaryAuthor(String primaryAuthor) { this.primaryAuthor = primaryAuthor; }
}