package dev.jam.accountservice.dao.repositories;

import dev.jam.accountservice.dao.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByTitle(String title);

    List<Event> findByCategoriesId(Long categoryId);
}
