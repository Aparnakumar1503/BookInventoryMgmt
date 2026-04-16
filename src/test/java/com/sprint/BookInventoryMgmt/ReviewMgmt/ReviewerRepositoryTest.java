package com.sprint.BookInventoryMgmt.ReviewMgmt;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.Reviewer;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.ReviewerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReviewerRepositoryTest {

    @Autowired
    private ReviewerRepository repository;

    @Test
    void testSaveReviewer() {
        Reviewer r = new Reviewer();
        r.setReviewerID(1);
        r.setName("Swarnalatha");
        r.setEmployedBy("Student");

        Reviewer saved = repository.save(r);

        assertNotNull(saved.getReviewerID());
    }

    @Test
    void testFindAll() {
        Reviewer r = new Reviewer();
        r.setReviewerID(2);
        r.setName("Test User");
        r.setEmployedBy("Company");

        repository.save(r);

        List<Reviewer> list = repository.findAll();

        assertFalse(list.isEmpty());
    }

    @Test
    void testUpdateReviewer() {
        Reviewer r = new Reviewer();
        r.setReviewerID(3);
        r.setName("Old Name");
        r.setEmployedBy("Old Company");

        Reviewer saved = repository.save(r);

        saved.setName("New Name");
        repository.save(saved);

        Reviewer updated = repository.findById(saved.getReviewerID())
                .orElseThrow();

        assertEquals("New Name", updated.getName());
    }

    @Test
    void testDeleteReviewer() {
        Reviewer r = new Reviewer();
        r.setReviewerID(4);
        r.setName("Delete Me");
        r.setEmployedBy("X");

        Reviewer saved = repository.save(r);

        repository.deleteById(saved.getReviewerID());

        assertFalse(repository.findById(saved.getReviewerID()).isPresent());
    }
}