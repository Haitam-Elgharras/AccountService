package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Event;
import dev.jam.accountservice.dao.entities.Review;
import dev.jam.accountservice.dao.repositories.EventRepository;
import dev.jam.accountservice.dao.repositories.ReviewRepository;
import dev.jam.accountservice.exceptions.EventNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements IEventService {
    private final EventRepository eventRepository;
    private final ReviewRepository reviewRepository;

    public EventServiceImpl(EventRepository eventRepository, ReviewRepository reviewRepository) {
        this.eventRepository = eventRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEventById(long id) throws EventNotFoundException {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Event with id " + id + " not found");
        }
        eventRepository.deleteById(id);
    }

    @Override
    public Event updateEvent(Event event) throws EventNotFoundException {
        if (!eventRepository.existsById(event.getId())) {
            throw new EventNotFoundException("Event with id " + event.getId() + " not found");
        }
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(long id) throws EventNotFoundException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event with id " + id + " not found"));
    }

    @Override
    public Event getEventByName(String title) throws EventNotFoundException {
        return eventRepository.findByTitle(title)
                .orElseThrow(() -> new EventNotFoundException("Event with title " + title + " not found"));
    }

    @Override
    public List<Event> getEventsByCategory(Long categoryId) {
        return eventRepository.findByCategoriesId(categoryId);
    }

    @Override
    public Event addReviewToEvent(Long eventId, Review review) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event with id " + eventId + " not found"));

        review.setEvent(event);
        reviewRepository.save(review);

        event.getReviews().add(review);
        return eventRepository.save(event);
    }

    @Override
    public void deleteReviewFromEvent(Long eventId, Long reviewId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event with id " + eventId + " not found"));
        event.getReviews().removeIf(review -> review.getId().equals(reviewId));
        eventRepository.save(event);
    }

}