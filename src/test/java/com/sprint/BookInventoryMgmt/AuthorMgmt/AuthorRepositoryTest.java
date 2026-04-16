package com.sprint.BookInventoryMgmt.AuthorMgmt;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.Author;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.AuthorRepository;
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
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setPhoto("photo.jpg");

        Author saved = repository.save(author);

        assertNotNull(saved.getAuthorId());
        assertEquals("John", saved.getFirstName());
    }

    @Test
    void testFindAllAuthors() {
        Author a1 = new Author(null, "A", "One", null);
        Author a2 = new Author(null, "B", "Two", null);

        repository.save(a1);
        repository.save(a2);

        List<Author> list = repository.findAll();

        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author(null, "Delete", "Me", null);
        Author saved = repository.save(author);

        repository.deleteById(saved.getAuthorId());

        assertFalse(repository.findById(saved.getAuthorId()).isPresent());
    }
}