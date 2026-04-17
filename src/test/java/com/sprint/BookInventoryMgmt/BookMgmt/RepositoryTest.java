package com.sprint.BookInventoryMgmt.BookMgmt;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.*;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTest {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookRepository bookRepository;

    static State state;
    static Category category;
    static Publisher publisher;
    static Book book;

    @Test
    @Order(1)
    void createData() {

        State s = new State();
        s.setStateCode("TN");
        s.setStateName("Tamil Nadu");
        state = stateRepository.save(s);

        Category c = new Category();
        c.setCatId(1);
        c.setCatDescription("Fiction");
        category = categoryRepository.save(c);

        Publisher p = new Publisher();
        p.setPublisherId(100);
        p.setName("Pearson");
        p.setCity("Chennai");
        p.setState(state);
        publisher = publisherRepository.save(p);

        Book b = new Book();
        b.setIsbn("ISBN123");
        b.setTitle("Spring Boot Basics");
        b.setDescription("Learn Spring Boot");
        b.setEdition("1st");
        b.setCategory(category);
        b.setPublisher(publisher);

        book = bookRepository.save(b);

        assertTrue(bookRepository.findById("ISBN123").isPresent());
    }

    @Test
    @Order(2)
    void readData() {

        Optional<Book> found = bookRepository.findById("ISBN123");

        assertTrue(found.isPresent());
        assertEquals("Spring Boot Basics", found.get().getTitle());
    }

    @Test
    @Order(3)
    void updateData() {

        Optional<Book> optional = bookRepository.findById("ISBN123");
        assertTrue(optional.isPresent(), "Book should exist before update");

        Book existing = optional.get();
        existing.setTitle("Spring Boot Advanced");
        bookRepository.save(existing);

        assertEquals("Spring Boot Advanced",
                bookRepository.findById("ISBN123").get().getTitle());
    }

    @Test
    @Order(4)
    void deleteData() {

        bookRepository.deleteById("ISBN123");

        assertFalse(bookRepository.findById("ISBN123").isPresent());
    }
}