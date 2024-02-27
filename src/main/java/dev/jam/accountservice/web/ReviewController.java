package dev.jam.accountservice.web;

import dev.jam.accountservice.dao.entities.Review;
import dev.jam.accountservice.service.IReviewService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Transactional
public class ReviewController {
    private final IReviewService reviewService;

    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{eventId}/users/{userId}")
    public Review addReviewToEvent(@PathVariable Long eventId, @PathVariable Long userId, @RequestBody Review review) {
        return reviewService.addReviewToEvent(eventId, userId, review);
    }
    @DeleteMapping("/{id}")
    public void removeReviewFromEvent(@PathVariable Long id) {
        reviewService.removeReviewFromEvent(id);
    }

    @PutMapping
    public Review updateReview(@RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // get review by user id
    @GetMapping("/users/{userId}")
    public List<Review> getReviewByUserId(@PathVariable Long userId) {
        return reviewService.getReviewByUserId(userId);
    }

    // get review by event id
    @GetMapping("/events/{eventId}")
    public List<Review> getReviewByEventId(@PathVariable Long eventId) {
        return reviewService.getReviewByEventId(eventId);
    }
}