package com.sprint.BookInventoryMgmt.AuthorMgmt;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.Author;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void testSaveAuthor() {

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        Author saved = authorRepository.save(author);

        assertNotNull(saved.getAuthorId());
        assertEquals("John", saved.getFirstName());
    }

    @Test
    void testFindAllAuthors() {

        Author author = new Author();
        author.setFirstName("Jane");
        author.setLastName("Smith");

        authorRepository.save(author);

        assertFalse(authorRepository.findAll().isEmpty());
    }
}
