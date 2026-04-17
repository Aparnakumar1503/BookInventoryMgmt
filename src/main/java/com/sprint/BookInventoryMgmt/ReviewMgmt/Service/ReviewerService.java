package com.sprint.BookInventoryMgmt.ReviewMgmt.Service;

import com.sprint.BookInventoryMgmt.ReviewMgmt.Entity.Reviewer;
import java.util.List;

public interface ReviewerService {

    Reviewer addReviewer(Reviewer reviewer);

    Reviewer getReviewerById(int reviewerId);

    List<Reviewer> getAllReviewers();

    Reviewer updateReviewer(int reviewerId, Reviewer reviewer);

    void deleteReviewer(int reviewerId);
}