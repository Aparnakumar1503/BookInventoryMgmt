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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private Author author1;
    private Author author2;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        State state = new State();
        state.setStateCode("TN");
        state.setStateName("Tamil Nadu");
        stateRepository.saveAndFlush(state);

        Category category = new Category();
        category.setCatId(1);
        category.setCatDescription("Programming");
        categoryRepository.saveAndFlush(category);

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setName("Test Publisher");
        publisher.setCity("Chennai");
        publisher.setState(state);
        publisherRepository.saveAndFlush(publisher);

        book1 = bookRepository.saveAndFlush(new Book("1-111-11111-4", "Book One", "Desc", "1st", category, publisher));
        book2 = bookRepository.saveAndFlush(new Book("1-222-32443-7", "Book Two", "Desc", "1st", category, publisher));
        author1 = authorRepository.saveAndFlush(new Author(null, "John", "Doe", null));
        author2 = authorRepository.saveAndFlush(new Author(null, "Jane", "Smith", null));
    }

    @Test
    void derivedQueries_findByIsbn_andAuthorId_returnMappings() {
        bookAuthorRepository.saveAndFlush(new BookAuthor(book1.getIsbn(), author1.getAuthorId(), null, null, "Y"));
        bookAuthorRepository.saveAndFlush(new BookAuthor(book1.getIsbn(), author2.getAuthorId(), null, null, "N"));
        bookAuthorRepository.saveAndFlush(new BookAuthor(book2.getIsbn(), author1.getAuthorId(), null, null, "N"));

        assertEquals(2, bookAuthorRepository.findByIsbn(book1.getIsbn()).size());
        assertEquals(2, bookAuthorRepository.findByAuthorId(author1.getAuthorId()).size());
    }

    @Test
    void customQueries_findBooksByAuthor_andPrimaryAuthor_returnExpectedRows() {
        bookAuthorRepository.saveAndFlush(new BookAuthor(book1.getIsbn(), author1.getAuthorId(), null, null, "Y"));
        bookAuthorRepository.saveAndFlush(new BookAuthor(book2.getIsbn(), author1.getAuthorId(), null, null, "N"));

        assertEquals(2, bookAuthorRepository.findBooksByAuthorId(author1.getAuthorId()).size());
        assertNotNull(bookAuthorRepository.findPrimaryAuthorByIsbn(book1.getIsbn()));
    }

    @Test
    void deleteByIsbn_removesAllMappingsForBook() {
        bookAuthorRepository.saveAndFlush(new BookAuthor(book1.getIsbn(), author1.getAuthorId(), null, null, "Y"));
        bookAuthorRepository.saveAndFlush(new BookAuthor(book1.getIsbn(), author2.getAuthorId(), null, null, "N"));

        bookAuthorRepository.deleteByIsbn(book1.getIsbn());

        assertTrue(bookAuthorRepository.findByIsbn(book1.getIsbn()).isEmpty());
    }


}
