package com.sprint.bookinventorymgmt.authormgmt.repository;

import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository repository;

    @Test
    void testSaveAuthor() {
        Author author = new Author(null, "John", "Doe", null);
        Author saved = repository.saveAndFlush(author);

        assertNotNull(saved);
        assertNotNull(saved.getAuthorId()); // auto-generated
        assertEquals("John", saved.getFirstName());
        assertEquals("Doe", saved.getLastName());
    }

    @Test
    void testFindByLastName() {
        repository.saveAndFlush(new Author(null, "John", "Doe", null));
        repository.saveAndFlush(new Author(null, "Jane", "Doe", null));

        List<Author> result = repository.findByLastName("Doe");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    void testFindByFirstName() {
        repository.saveAndFlush(new Author(null, "John", "Doe", null));

        List<Author> result = repository.findByFirstName("John");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("John", result.get(0).getFirstName());
    }

    // Custom Query 1 — find by first and last name
    @Test
    void testFindByFirstNameAndLastName() {
        repository.saveAndFlush(new Author(null, "John", "Doe", null));

        Author result = repository.findByFirstNameAndLastName("John", "Doe");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    // Custom Query 2 — search by last name keyword
    @Test
    void testSearchByLastName() {
        repository.saveAndFlush(new Author(null, "John", "Doe", null));
        repository.saveAndFlush(new Author(null, "Jane", "Doeson", null));

        List<Author> result = repository.searchByLastName("doe");

        assertNotNull(result);
        assertEquals(2, result.size()); // both contain "doe"
    }

    // Custom Query 3 — count all authors
    @Test
    void testCountAllAuthors() {
        repository.saveAndFlush(new Author(null, "John", "Doe", null));
        repository.saveAndFlush(new Author(null, "Jane", "Smith", null));

        Long count = repository.countAllAuthors();

        assertNotNull(count);
        assertEquals(2L, count);
    }
}