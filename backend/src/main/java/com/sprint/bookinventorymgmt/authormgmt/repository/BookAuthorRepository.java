package com.sprint.bookinventorymgmt.authormgmt.repository;

import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthorId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    List<BookAuthor> findByIsbn(String isbn);

    @Query("SELECT ba FROM BookAuthor ba WHERE ba.authorId = :authorId")
    List<BookAuthor> findBooksByAuthorId(@Param("authorId") Integer authorId);

    @Query("SELECT ba FROM BookAuthor ba WHERE ba.isbn = :isbn AND ba.primaryAuthor = 'Y'")
    BookAuthor findPrimaryAuthorByIsbn(@Param("isbn") String isbn);

    @Modifying
    @Transactional
    @Query("DELETE FROM BookAuthor ba WHERE ba.isbn = :isbn AND ba.authorId = :authorId")
    int deleteByIsbnAndAuthorId(@Param("isbn") String isbn, @Param("authorId") Integer authorId);
}
