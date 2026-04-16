package com.sprint.BookInventoryMgmt.AuthorMgmt;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.Author;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthor;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.AuthorRepository;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.BookAuthorRepository;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class BookAuthorRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Test
    void testSaveBookAuthor() {

        // 🔹 CREATE BOOK
        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Java");

        Book savedBook = bookRepository.save(book);

        // 🔹 CREATE AUTHOR
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        Author savedAuthor = authorRepository.save(author);

        // 🔹 CREATE MAPPING
        BookAuthor ba = new BookAuthor();
        ba.setIsbn(savedBook.getIsbn());
        ba.setAuthorId(savedAuthor.getAuthorId());
        ba.setPrimaryAuthor("Y");

        BookAuthor saved = bookAuthorRepository.save(ba);

        // ASSERT
        assertNotNull(saved);
        assertEquals("Y", saved.getPrimaryAuthor());
    }
}
