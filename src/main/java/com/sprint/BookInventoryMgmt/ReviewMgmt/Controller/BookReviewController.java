package com.sprint.BookInventoryMgmt.ReviewMgmt.Controller;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.BookReview;
import com.sprint.BookInventoryMgmt.ReviewMgmt.Service.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin(origins = "*")
public class BookReviewController {

    @Autowired
    private BookReviewService reviewService;


    @PostMapping("/{isbn}/reviews")
    public BookReview addReview(@PathVariable String isbn,
                                @RequestBody BookReview review) {

        review.setIsbn(isbn);
        return reviewService.addReview(review);
    }

    @GetMapping("/{isbn}/reviews")
    public List<BookReview> getReviewsByISBN(@PathVariable String isbn) {
        return reviewService.getReviewsByISBN(isbn);
    }

    @GetMapping("/reviews/reviewer/{reviewerId}")
    public List<BookReview> getReviewsByReviewer(@PathVariable int reviewerId) {
        return reviewService.getReviewsByReviewer(reviewerId);
    }

    @GetMapping("/reviews")
    public List<BookReview> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @DeleteMapping("/reviews/{id}")
    public String deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
        return "Review deleted successfully";
    }
}