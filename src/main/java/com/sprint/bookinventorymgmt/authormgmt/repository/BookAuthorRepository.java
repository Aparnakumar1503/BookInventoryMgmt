package com.sprint.bookinventorymgmt.authormgmt.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthorId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    List<BookAuthor> findByIsbn(String isbn);
    List<BookAuthor> findByAuthorId(Integer authorId);

    // Custom Query 1 — Find all books by a specific author
    @Query("SELECT ba FROM BookAuthor ba WHERE ba.authorId = :authorId")
    List<BookAuthor> findBooksByAuthorId(@Param("authorId") Integer authorId);

    // Custom Query 2 — Find primary author of a book
    @Query("SELECT ba FROM BookAuthor ba WHERE ba.isbn = :isbn AND ba.primaryAuthor = 'Y'")
    BookAuthor findPrimaryAuthorByIsbn(@Param("isbn") String isbn);

    // Custom Query 3 — Delete all entries by isbn
    @Modifying
    @Transactional
    @Query("DELETE FROM BookAuthor ba WHERE ba.isbn = :isbn")
    void deleteByIsbn(@Param("isbn") String isbn);
}

