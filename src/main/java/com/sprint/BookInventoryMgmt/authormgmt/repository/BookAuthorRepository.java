package com.sprint.BookInventoryMgmt.authormgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.BookInventoryMgmt.authormgmt.entity.BookAuthor;
import com.sprint.BookInventoryMgmt.authormgmt.entity.BookAuthorId;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {

}

