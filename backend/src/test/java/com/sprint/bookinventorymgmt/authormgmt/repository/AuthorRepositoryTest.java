package com.sprint.bookinventorymgmt.authormgmt.repository;

import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    void derivedQueries_findByFirstName_andLastName_returnMatches() {
        repository.saveAndFlush(new Author(null, "John", "Doe", null));
        repository.saveAndFlush(new Author(null, "Jane", "Doe", null));

        assertEquals(2, repository.findByLastName("Doe").size());
        assertEquals(1, repository.findByFirstName("John").size());
    }

    @Test
    void customQuery_findByFirstNameAndLastName_returnsSingleAuthor() {
        repository.saveAndFlush(new Author(null, "John", "Doe", null));

        Author result = repository.findByFirstNameAndLastName("John", "Doe");

        assertNotNull(result);
        assertEquals("Doe", result.getLastName());
    }

}
