package com.sprint.BookInventoryMgmt.ReviewMgmt.Controller;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.Reviewer;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviewers")
@CrossOrigin(origins = "*")
public class ReviewerController {

    @Autowired
    private ReviewerService reviewerService;

    @PostMapping
    public Reviewer addReviewer(@RequestBody Reviewer reviewer) {
        return reviewerService.addReviewer(reviewer);
    }

    @GetMapping("/{reviewerId}")
    public Reviewer getReviewerById(@PathVariable int reviewerId) {
        return reviewerService.getReviewerById(reviewerId);
    }

    @GetMapping
    public List<Reviewer> getAllReviewers() {
        return reviewerService.getAllReviewers();
    }

    @PutMapping("/{reviewerId}")
    public Reviewer updateReviewer(@PathVariable int reviewerId,
                                   @RequestBody Reviewer reviewer) {
        return reviewerService.updateReviewer(reviewerId, reviewer);
    }

    @DeleteMapping("/{reviewerId}")
    public String deleteReviewer(@PathVariable int reviewerId) {
        reviewerService.deleteReviewer(reviewerId);
        return "Reviewer deleted successfully";
    }
}