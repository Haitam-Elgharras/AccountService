package dev.jam.accountservice.dao.repositories;

import dev.jam.accountservice.dao.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // find events by rating
    List<Review> findByRating(int rating);

    // find events by user id
    List<Review> findByUserId(Long userId);

    // find events by event id
    List<Review> findByEventId(Long eventId);
}
