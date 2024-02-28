package dev.jam.accountservice.web;

import dev.jam.accountservice.dao.entities.Category;
import dev.jam.accountservice.dao.entities.Company;
import dev.jam.accountservice.dao.entities.Event;
import dev.jam.accountservice.dao.entities.Review;
import dev.jam.accountservice.dao.repositories.CategoryRepository;
import dev.jam.accountservice.exceptions.EventNotFoundException;
import dev.jam.accountservice.exceptions.UserAccessDeniedException;
import dev.jam.accountservice.service.ICompanyService;
import dev.jam.accountservice.service.IEventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@Transactional
public class EventController {
    IEventService eventService;
    ICompanyService companyService;
    CategoryRepository cr;

    public EventController(IEventService eventService, ICompanyService companyService,CategoryRepository cr){
        this.eventService = eventService;
        this.companyService = companyService;
        this.cr= cr;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    public List<Event> getEventsByCategory(@PathVariable Long categoryId){
        return eventService.getEventsByCategory(categoryId);
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_COMPANY_OWNER')")
    public Event getEventById(@PathVariable Long eventId){
        return eventService.getEventById(eventId);
    }

    // query param for category id
    @PostMapping
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public Event createEvent(@RequestBody Event event, @RequestParam Long categoryId){
        // get the authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = auth.getName();
        // get the company of the authenticated user
        Company company = companyService.getCompanyByUserEmail(authenticatedUsername);
        // set the company of the event
        event.setCompany(company);

        // save the event to generate an ID
        event = eventService.addEvent(event);

        // set the category of the event
        Category category = cr.findById(categoryId).orElse(null);
        if(category!=null)
            eventService.addCategoryToEvent(event.getId(),category.getId());

        // save the event again with the category
        return eventService.updateEvent(event);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody Event event){
        // check if the event exists
        Event existingEvent = eventService.getEventById(eventId);
        if (existingEvent == null) {
            throw new EventNotFoundException("Event Not Found");
        }
        // check if the user who send the request is the owner of the event
        // get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = auth.getName();
        if (!existingEvent.getCompany().getUser().getEmail().equals(authenticatedUsername)) {
            throw new UserAccessDeniedException("Access Denied");
        }

        event.setId(eventId);
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public void deleteEvent(@PathVariable Long eventId){
        // check if the event exists
        Event existingEvent = eventService.getEventById(eventId);
        if (existingEvent == null) {
            throw new EventNotFoundException("Event Not Found");
        }
        // check if the user who send the request is the owner of the event
        // get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = auth.getName();
        if (!existingEvent.getCompany().getUser().getEmail().equals(authenticatedUsername)) {
            throw new UserAccessDeniedException("Access Denied");
        }

        eventService.deleteEventById(eventId);
    }
}
