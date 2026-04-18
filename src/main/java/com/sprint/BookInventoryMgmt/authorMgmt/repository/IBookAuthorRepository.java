package com.sprint.BookInventoryMgmt.authorMgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.BookInventoryMgmt.authorMgmt.entity.BookAuthor;
import com.sprint.BookInventoryMgmt.authorMgmt.entity.BookAuthorId;

public interface IBookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
}

