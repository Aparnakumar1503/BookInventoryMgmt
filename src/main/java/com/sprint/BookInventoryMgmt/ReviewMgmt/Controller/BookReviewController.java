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

    // ✅ ADD REVIEW
    @PostMapping("/{isbn}/reviews")
    public BookReview addReview(@PathVariable String isbn,
                                @RequestBody BookReview review) {

        review.setIsbn(isbn); // 🔥 IMPORTANT
        return reviewService.addReview(review);
    }

    // ✅ GET REVIEWS BY ISBN
    @GetMapping("/{isbn}/reviews")
    public List<BookReview> getReviewsByISBN(@PathVariable String isbn) {
        return reviewService.getReviewsByISBN(isbn);
    }

    // ✅ OPTIONAL: GET ALL
    @GetMapping("/reviews/all")
    public List<BookReview> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // ✅ DELETE
    @DeleteMapping("/reviews/{id}")
    public String deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
        return "Review deleted successfully";
    }
}

