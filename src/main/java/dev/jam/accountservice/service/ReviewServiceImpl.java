package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Event;
import dev.jam.accountservice.dao.entities.Review;
import dev.jam.accountservice.dao.entities.User;
import dev.jam.accountservice.dao.repositories.ReviewRepository;
import dev.jam.accountservice.dao.repositories.UserRepository;
import dev.jam.accountservice.dao.repositories.EventRepository;
import dev.jam.accountservice.exceptions.EventNotFoundException;
import dev.jam.accountservice.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements IReviewService{

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Review addReviewToEvent(Long eventId, Long userId, Review review) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new UserNotFoundException("User with id " + userId + " not found");

        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty())
            throw new EventNotFoundException("Event with id " + eventId + " not found");

        review.setUser(user.get());
        review.setEvent(event.get());
        return reviewRepository.save(review);
    }

    @Override
    public void removeReviewFromEvent(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public List<Review> getReviewByEventId(Long eventId) {
        return reviewRepository.findByEventId(eventId);
    }

}