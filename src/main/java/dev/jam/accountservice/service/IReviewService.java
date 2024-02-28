package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Review;

import java.util.List;

public interface IReviewService {
    Review addReviewToEvent(Long eventId, Long userId, Review review);

    void removeReviewFromEvent(Long reviewId);


    List<Review> getAllReviews();

    Review updateReview(Long id, Review review);

    Review getReviewById(Long id);

    List<Review> getReviewByUserId(Long userId);
    List<Review> getReviewByEventId(Long eventId);
}
