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

    state = stateRepository.save(
            State.builder()
                    .stateCode("TN")
                    .stateName("Tamil Nadu")
                    .build()
    );

    category = categoryRepository.save(
            Category.builder()
                    .catId(1)
                    .catDescription("Fiction")
                    .build()
    );

    publisher = publisherRepository.save(
            Publisher.builder()
                    .publisherId(100)
                    .name("Pearson")
                    .city("Chennai")
                    .state(state)
                    .build()
    );

    book = bookRepository.save(
            Book.builder()
                    .isbn("ISBN123")
                    .title("Spring Boot Basics")
                    .description("Learn Spring Boot")
                    .edition("1st")
                    .category(category)
                    .publisher(publisher)
                    .build()
    );

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