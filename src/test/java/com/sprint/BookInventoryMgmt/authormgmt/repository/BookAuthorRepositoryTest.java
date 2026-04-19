package com.sprint.BookInventoryMgmt.authormgmt.repository;

import com.sprint.BookInventoryMgmt.authormgmt.entity.Author;
import com.sprint.BookInventoryMgmt.authormgmt.entity.BookAuthor;
import com.sprint.BookInventoryMgmt.bookmgmt.entity.Book;
import com.sprint.BookInventoryMgmt.bookmgmt.entity.Category;
import com.sprint.BookInventoryMgmt.bookmgmt.entity.Publisher;
import com.sprint.BookInventoryMgmt.bookmgmt.repository.BookRepository;
import com.sprint.BookInventoryMgmt.bookmgmt.repository.CategoryRepository;
import com.sprint.BookInventoryMgmt.bookmgmt.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookAuthorRepositoryTest {

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;  // needed for Book FK

    @Autowired
    private PublisherRepository publisherRepository; // needed for Book FK

    private Author savedAuthor1;
    private Author savedAuthor2;
    private Book savedBook1;
    private Book savedBook2;

    @BeforeEach
    void setUp() {
        // Step 1 — save Category first (Book FK dependency)
        Category category = new Category();
        category.setCatId(1);
        category.setCatDescription("Programming");
        categoryRepository.saveAndFlush(category);

        // Step 2 — save Publisher (Book FK dependency)
        Publisher publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setName("Test Publisher");
        publisher.setCity("Chennai");
        publisherRepository.saveAndFlush(publisher);

        // Step 3 — save Books (BookAuthor FK dependency)
        savedBook1 = bookRepository.saveAndFlush(
                new Book("1-111-11111-4", "Book One", "Desc One", "1st", category, publisher));
        savedBook2 = bookRepository.saveAndFlush(
                new Book("1-222-32443-7", "Book Two", "Desc Two", "2nd", category, publisher));

        // Step 4 — save Authors (BookAuthor FK dependency)
        savedAuthor1 = authorRepository.saveAndFlush(
                new Author(null, "John", "Doe", null));
        savedAuthor2 = authorRepository.saveAndFlush(
                new Author(null, "Jane", "Smith", null));
    }

    @Test
    void testSaveBookAuthor() {
        BookAuthor bookAuthor = new BookAuthor(
                savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y");
        BookAuthor saved = bookAuthorRepository.saveAndFlush(bookAuthor);

        assertNotNull(saved);
        assertEquals(savedBook1.getIsbn(), saved.getIsbn());
        assertEquals(savedAuthor1.getAuthorId(), saved.getAuthorId());
    }

    @Test
    void testFindByIsbn() {
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y"));
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor2.getAuthorId(), null, null, "N"));

        List<BookAuthor> result = bookAuthorRepository.findByIsbn(savedBook1.getIsbn());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(savedBook1.getIsbn(), result.get(0).getIsbn());
    }

    @Test
    void testFindByAuthorId() {
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y"));
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook2.getIsbn(), savedAuthor1.getAuthorId(), null, null, "N"));

        List<BookAuthor> result = bookAuthorRepository.findByAuthorId(savedAuthor1.getAuthorId());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(savedAuthor1.getAuthorId(), result.get(0).getAuthorId());
    }

    // Custom Query 1 — find books by authorId
    @Test
    void testFindBooksByAuthorId() {
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y"));
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook2.getIsbn(), savedAuthor1.getAuthorId(), null, null, "N"));

        List<BookAuthor> result = bookAuthorRepository.findBooksByAuthorId(savedAuthor1.getAuthorId());

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // Custom Query 2 — find primary author by isbn
    @Test
    void testFindPrimaryAuthorByIsbn() {
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y"));
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor2.getAuthorId(), null, null, "N"));

        BookAuthor result = bookAuthorRepository.findPrimaryAuthorByIsbn(savedBook1.getIsbn());

        assertNotNull(result);
        assertEquals("Y", result.getPrimaryAuthor());
        assertEquals(savedAuthor1.getAuthorId(), result.getAuthorId());
    }

    // Custom Query 3 — delete by isbn
    @Test
    void testDeleteByIsbn() {
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y"));
        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor2.getAuthorId(), null, null, "N"));

        bookAuthorRepository.deleteByIsbn(savedBook1.getIsbn());

        List<BookAuthor> result = bookAuthorRepository.findByIsbn(savedBook1.getIsbn());

        assertTrue(result.isEmpty());
    }
}