package com.sprint.bookinventorymgmt.authormgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthorId;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {

}

