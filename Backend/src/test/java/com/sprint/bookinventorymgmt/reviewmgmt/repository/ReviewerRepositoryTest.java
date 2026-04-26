package com.sprint.bookinventorymgmt.reviewmgmt.repository;

import com.sprint.bookinventorymgmt.reviewmgmt.entity.Reviewer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ReviewerRepositoryTest {

    @Autowired
    private ReviewerRepository repository;

    @Test
    void findByEmployedByIgnoreCaseContaining_returnsMatchingReviewers() {
        repository.save(new Reviewer(1, "Alice", "BookWorld"));
        repository.save(new Reviewer(2, "Bob", "World Books"));

        assertEquals(2, repository.findByEmployedByIgnoreCaseContaining("world").size());
    }
}
