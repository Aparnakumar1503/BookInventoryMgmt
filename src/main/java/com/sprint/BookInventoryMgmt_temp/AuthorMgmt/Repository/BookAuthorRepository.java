package com.sprint.BookInventoryMgmt.AuthorMgmt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthor;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthorId;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
}

