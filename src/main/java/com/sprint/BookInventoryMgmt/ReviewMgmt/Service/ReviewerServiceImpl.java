package com.sprint.BookInventoryMgmt.ReviewMgmt.Service;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.Reviewer;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService{
    @Autowired
    private ReviewerRepository reviewerRepository;

    public Reviewer addReviewer(Reviewer reviewer) {
        return reviewerRepository.save(reviewer);
    }

    public Reviewer getReviewerById(int reviewerId) {
        return reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
    }

    public List<Reviewer> getAllReviewers() {
        return reviewerRepository.findAll();
    }

    public Reviewer updateReviewer(int reviewerId, Reviewer reviewer) {
        Reviewer existing = getReviewerById(reviewerId);
        existing.setName(reviewer.getName());
        existing.setEmployedBy(reviewer.getEmployedBy());
        return reviewerRepository.save(existing);
    }

    public void deleteReviewer(int reviewerId) {
        reviewerRepository.deleteById(reviewerId);
    }
}