package dev.jam.accountservice.web;

import dev.jam.accountservice.dao.entities.Review;
import dev.jam.accountservice.dao.entities.User;
import dev.jam.accountservice.exceptions.UserAccessDeniedException;
import dev.jam.accountservice.service.IReviewService;
import dev.jam.accountservice.service.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reviews")
@Transactional
public class ReviewController {
    private final IReviewService reviewService;
    private final IUserService userService;

    public ReviewController(IReviewService reviewService, IUserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{eventId}/users")
    public Review addReviewToEvent(@PathVariable Long eventId, @RequestBody Review review) {
        Long userId = getAuthUserId();
        return reviewService.addReviewToEvent(eventId, userId, review);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public void removeReviewFromEvent(@PathVariable Long id) {
        Long userId = getAuthUserId();
        // check if the review belongs to the user
        Review review = reviewService.getReviewById(id);
        if (review == null)
            throw new RuntimeException("Review with id " + id + " not found");

        if (!Objects.equals(review.getUser().getId(), userId))
            throw new UserAccessDeniedException("You are not allowed to delete this review");

        reviewService.removeReviewFromEvent(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review review) {
        Long userId = getAuthUserId();
        // check if the review belongs to the user
        Review reviewToUpdate = reviewService.getReviewById(id);
        if (reviewToUpdate == null)
            throw new RuntimeException("Review with id " + id + " not found");

        if (!Objects.equals(reviewToUpdate.getUser().getId(), userId))
            throw new UserAccessDeniedException("You are not allowed to update this review");

        return reviewService.updateReview(id, review);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    @GetMapping("/users/{userId}")
    public List<Review> getReviewByUserId(@PathVariable Long userId) {
        return reviewService.getReviewByUserId(userId);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    @GetMapping("/events/{eventId}")
    public List<Review> getReviewByEventId(@PathVariable Long eventId) {
        return reviewService.getReviewByEventId(eventId);
    }

    private Long getAuthUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        return user.getId();
    }

}