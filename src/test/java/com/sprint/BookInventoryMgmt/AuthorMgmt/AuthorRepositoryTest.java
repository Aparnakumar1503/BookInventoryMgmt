package com.sprint.BookInventoryMgmt.AuthorMgmt;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.Author;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Test Save Author")
    void testSaveAuthor() {
        Author author = new Author();
        author.setName("John Doe");

        Author saved = authorRepository.save(author);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("John Doe", saved.getName());
    }

    @Test
    @DisplayName("Test Find By Id")
    void testFindById() {
        Author author = new Author();
        author.setName("Jane");

        Author saved = authorRepository.save(author);

        Optional<Author> result = authorRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getName());
    }

    @Test
    @DisplayName("Test Delete Author")
    void testDeleteAuthor() {
        Author author = new Author();
        author.setName("Delete Test");

        Author saved = authorRepository.save(author);

        authorRepository.deleteById(saved.getId());

        Optional<Author> result = authorRepository.findById(saved.getId());

        assertFalse(result.isPresent());
    }
}
