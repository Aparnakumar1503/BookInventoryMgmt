package com.sprint.BookInventoryMgmt.AuthorMgmt.Entity;

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

	@Column(name = "PrimaryAuthor")
	private String primaryAuthor;
}

