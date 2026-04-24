package com.sprint.bookinventorymgmt.reviewmgmt.repository;

import com.sprint.bookinventorymgmt.reviewmgmt.entity.BookReview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookReviewRepositoryTest {

    @Autowired
    private BookReviewRepository repository;

    @Test
    void derivedQueries_findByIsbn_andReviewerId_returnMatches() {
        repository.save(new BookReview(null, "111", 1, 9, "Nice"));
        repository.save(new BookReview(null, "111", 2, 8, "Good"));

        assertEquals(2, repository.findByIsbn("111").size());
        assertEquals(1, repository.findByReviewerID(1).size());
    }

    @Test
    void customQuery_findByMaxRating_returnsHighestRatedReviews() {
        repository.save(new BookReview(null, "222", 1, 5, "Okay"));
        repository.save(new BookReview(null, "333", 2, 10, "Great"));

        assertEquals(1, repository.findByMaxRating().size());
        assertEquals(10, repository.findByMaxRating().get(0).getRating());
    }
}
