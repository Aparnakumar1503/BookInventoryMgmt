package com.sprint.BookInventoryMgmt.ReviewMgmt.Service;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.Reviewer;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Repository.ReviewerRepository;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Exception.ReviewerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerServiceImpl implements ReviewerService {

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Override
    public Reviewer addReviewer(Reviewer reviewer) {
        return reviewerRepository.save(reviewer);
    }

    @Override
    public Reviewer getReviewerById(int reviewerId) {
        return reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFoundException("Reviewer not found with ID: " + reviewerId));
    }

    @Override
    public List<Reviewer> getAllReviewers() {
        return reviewerRepository.findAll();
    }

    @Override
    public Reviewer updateReviewer(int reviewerId, Reviewer reviewer) {

        Reviewer existing = getReviewerById(reviewerId);

        existing.setName(reviewer.getName());
        existing.setEmployedBy(reviewer.getEmployedBy());

        return reviewerRepository.save(existing);
    }

    @Override
    public void deleteReviewer(int reviewerId) {
        if (!reviewerRepository.existsById(reviewerId)) {
            throw new ReviewerNotFoundException("Reviewer not found with ID: " + reviewerId);
        }
        reviewerRepository.deleteById(reviewerId);
    }
}