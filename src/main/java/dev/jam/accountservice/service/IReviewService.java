package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Review;

import java.util.List;

public interface IReviewService {
    Review addReviewToEvent(Long eventId, Long userId, Review review);

    void removeReviewFromEvent(Long reviewId);

    Review updateReview(Review review);

    List<Review> getAllReviews();

    Review getReviewById(Long id);

    List<Review> getReviewByUserId(Long userId);
    List<Review> getReviewByEventId(Long eventId);
}
