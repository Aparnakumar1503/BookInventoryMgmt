package com.sprint.bookinventorymgmt.authormgmt.entity;


import lombok.*;

import java.io.Serializable;
import java.util.Objects;


public class BookAuthorId implements Serializable {

	private String isbn;
	private Integer authorId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BookAuthorId)) return false;
		BookAuthorId that = (BookAuthorId) o;
		return Objects.equals(isbn, that.isbn) &&
				Objects.equals(authorId, that.authorId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn, authorId);
	}
}


