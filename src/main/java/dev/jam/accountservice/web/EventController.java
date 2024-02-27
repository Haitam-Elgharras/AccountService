package dev.jam.accountservice.web;

import dev.jam.accountservice.dao.entities.Event;
import dev.jam.accountservice.dao.entities.Review;
import dev.jam.accountservice.service.IEventService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@Transactional
public class EventController {
    IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/category/{categoryId}")
    public List<Event> getEventsByCategory(@PathVariable Long categoryId){
        return eventService.getEventsByCategory(categoryId);
    }

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId){
        return eventService.getEventById(eventId);
    }

    @PostMapping()
    public Event createEvent(@RequestBody Event event){
        return eventService.addEvent(event);
    }

    @PutMapping()
    public Event updateEvent(@RequestBody Event event){
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId){
        eventService.deleteEventById(eventId);
    }
}
