package com.sprint.bookinventorymgmt.authormgmt.repository;

import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Book;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Category;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Publisher;
import com.sprint.bookinventorymgmt.bookmgmt.entity.State;
import com.sprint.bookinventorymgmt.bookmgmt.repository.BookRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.CategoryRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.PublisherRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.StateRepository;
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
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private StateRepository stateRepository;

    private Author savedAuthor1;
    private Author savedAuthor2;
    private Book savedBook1;
    private Book savedBook2;

    private State savedState;

    @BeforeEach
    void setUp() {

        // =========================
        // 1️⃣ STATE (FIXED - NO CONSTRUCTOR)
        // =========================
        State state = new State();
        state.setStateCode("TN");
        state.setStateName("Tamil Nadu");

        savedState = stateRepository.saveAndFlush(state);

        // =========================
        // 2️⃣ CATEGORY
        // =========================
        Category category = new Category();
        category.setCatId(1);
        category.setCatDescription("Programming");

        categoryRepository.saveAndFlush(category);

        // =========================
        // 3️⃣ PUBLISHER
        // =========================
        Publisher publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setName("Test Publisher");
        publisher.setCity("Chennai");
        publisher.setState(savedState);

        publisherRepository.saveAndFlush(publisher);

        // =========================
        // 4️⃣ BOOKS
        // =========================
        savedBook1 = bookRepository.saveAndFlush(
                new Book("1-111-11111-4", "Book One", "Desc One", "1st", category, publisher)
        );

        savedBook2 = bookRepository.saveAndFlush(
                new Book("1-222-32443-7", "Book Two", "Desc Two", "2nd", category, publisher)
        );

        // =========================
        // 5️⃣ AUTHORS
        // =========================
        savedAuthor1 = authorRepository.saveAndFlush(
                new Author(null, "John", "Doe", null)
        );

        savedAuthor2 = authorRepository.saveAndFlush(
                new Author(null, "Jane", "Smith", null)
        );
    }

    // =========================
    // SAVE
    // =========================
    @Test
    void testSaveBookAuthor() {

        BookAuthor bookAuthor = new BookAuthor(
                savedBook1.getIsbn(),
                savedAuthor1.getAuthorId(),
                null,
                null,
                "Y"
        );

        BookAuthor saved = bookAuthorRepository.saveAndFlush(bookAuthor);

        assertNotNull(saved);
        assertEquals(savedBook1.getIsbn(), saved.getIsbn());
        assertEquals(savedAuthor1.getAuthorId(), saved.getAuthorId());
    }

    // =========================
    // FIND BY ISBN
    // =========================
    @Test
    void testFindByIsbn() {

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y")
        );

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor2.getAuthorId(), null, null, "N")
        );

        List<BookAuthor> result = bookAuthorRepository.findByIsbn(savedBook1.getIsbn());

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // =========================
    // FIND BY AUTHOR ID
    // =========================
    @Test
    void testFindByAuthorId() {

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y")
        );

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook2.getIsbn(), savedAuthor1.getAuthorId(), null, null, "N")
        );

        List<BookAuthor> result = bookAuthorRepository.findByAuthorId(savedAuthor1.getAuthorId());

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // =========================
    // CUSTOM QUERY - BOOKS BY AUTHOR
    // =========================
    @Test
    void testFindBooksByAuthorId() {

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y")
        );

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook2.getIsbn(), savedAuthor1.getAuthorId(), null, null, "N")
        );

        List<BookAuthor> result =
                bookAuthorRepository.findBooksByAuthorId(savedAuthor1.getAuthorId());

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // =========================
    // PRIMARY AUTHOR
    // =========================
    @Test
    void testFindPrimaryAuthorByIsbn() {

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y")
        );

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor2.getAuthorId(), null, null, "N")
        );

        BookAuthor result =
                bookAuthorRepository.findPrimaryAuthorByIsbn(savedBook1.getIsbn());

        assertNotNull(result);
        assertEquals("Y", result.getPrimaryAuthor());
    }

    // =========================
    // DELETE
    // =========================
    @Test
    void testDeleteByIsbn() {

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor1.getAuthorId(), null, null, "Y")
        );

        bookAuthorRepository.saveAndFlush(
                new BookAuthor(savedBook1.getIsbn(), savedAuthor2.getAuthorId(), null, null, "N")
        );

        bookAuthorRepository.deleteByIsbn(savedBook1.getIsbn());

        List<BookAuthor> result =
                bookAuthorRepository.findByIsbn(savedBook1.getIsbn());

        assertTrue(result.isEmpty());
    }
}