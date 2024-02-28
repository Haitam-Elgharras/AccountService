package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Event;
import dev.jam.accountservice.dao.entities.Review;
import dev.jam.accountservice.exceptions.EventNotFoundException;

import java.util.List;

public interface IEventService {
    Event addEvent(Event event);

    void deleteEventById(long id) throws EventNotFoundException;
    Event updateEvent(Event event) throws EventNotFoundException;
    List<Event> getAllEvents();
    Event getEventById(long id) throws EventNotFoundException;
    Event getEventByName(String title) throws EventNotFoundException;

    List<Event> getEventsByCategory(Long categoryId);

    Event addReviewToEvent(Long eventId, Review review);
    void deleteReviewFromEvent(Long eventId, Long reviewId);

    // add category to event
    Event addCategoryToEvent(Long eventId, Long categoryId);
}
