package com.sprint.BookInventoryMgmt.authormgmt.entity;

import com.sprint.BookInventoryMgmt.bookmgmt.entity.Book;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookauthor")
@IdClass(BookAuthorId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}