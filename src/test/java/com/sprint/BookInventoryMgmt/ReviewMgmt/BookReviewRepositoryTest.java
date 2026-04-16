package com.sprint.BookInventoryMgmt.ReviewMgmt;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.BookReview;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.BookReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookReviewRepositoryTest {

    @Autowired
    private BookReviewRepository repository;

    @Test
    @DisplayName("Save Book Review Test")
    void testSaveReview() {
        BookReview review = new BookReview();
        review.setIsbn("123");
        review.setReviewerID(1);
        review.setRating(8);
        review.setComments("Good book");

        BookReview saved = repository.save(review);

        assertNotNull(saved.getId());
        assertEquals("123", saved.getIsbn());
        assertEquals(8, saved.getRating());
    }

    @Test
    @DisplayName("Find Review By ISBN Test")
    void testFindByISBN() {
        BookReview review = new BookReview();
        review.setIsbn("111");
        review.setReviewerID(1);
        review.setRating(9);
        review.setComments("Nice");

        repository.save(review);

        List<BookReview> list = repository.findByIsbn("111");

        assertFalse(list.isEmpty());
        assertEquals("111", list.get(0).getIsbn());
    }

    @Test
    @DisplayName("Update Book Review Test")
    void testUpdateReview() {
        BookReview review = new BookReview();
        review.setIsbn("222");
        review.setReviewerID(1);
        review.setRating(5);
        review.setComments("Okay");

        BookReview saved = repository.save(review);

        saved.setRating(9);
        saved.setComments("Updated review");
        repository.save(saved);

        BookReview updated = repository.findById(saved.getId())
                .orElseThrow();

        assertEquals(9, updated.getRating());
        assertEquals("Updated review", updated.getComments());
    }

    @Test
    @DisplayName("Delete Book Review Test")
    void testDeleteReview() {
        BookReview review = new BookReview();
        review.setIsbn("333");
        review.setReviewerID(1);
        review.setRating(7);
        review.setComments("Average");

        BookReview saved = repository.save(review);

        repository.deleteById(saved.getId());

        assertTrue(repository.findById(saved.getId()).isEmpty());
    }
}