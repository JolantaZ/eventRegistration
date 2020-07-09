package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.model.Event;
import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.repository.EventRepo;
import com.gmail.jolantazych.event_register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class EventController {

    private EventRepo eventRepo;
    private UserService userService;

    @Autowired
    public EventController(EventRepo eventRepo, UserService userService) {
        this.eventRepo = eventRepo;
        this.userService = userService;
    }

    @GetMapping("/eventSave")
    public String goToEventSave(Model model) {
        model.addAttribute("event", new Event());
        return "registerEventFormView";
    }


    @PostMapping("/eventSave")
    public String saveEvent(@ModelAttribute @Valid Event event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registerEventFormView";
        }

        Event newEvent = eventRepo.save(event);
        System.out.println("New event: " + newEvent);
        model.addAttribute("successMessage", "Event has been register successfully!");
        return "registerEventFormView";

    }

    //usuwanie z poziomu admin area - podaje id eventu
    //todo II opcja - na liście wydarzeń dla admina pojawia się przycisk delete

    @GetMapping("/eventDelete")
    public String goToEventDelete() {
        return "deleteEventFormView";
    }

    @PostMapping("/eventDelete")
    public String deleteEvent(@RequestParam String event_id, Model model) {
        Optional<Event> event;
        try {
            event = eventRepo.findByIdEvent(new Long(event_id));
        }
        catch (NumberFormatException exc) {
            throw new NumberFormatException("Value of ID must be a digit");
        }
        if (event.isPresent()) {
            eventRepo.delete(event.get());
        }
        else {
            throw new NoSuchElementException("Event with ID: " + event_id + " does not exist");
        }
        model.addAttribute("succesMessage", "Event has been successfully deleted!");
        return "deleteEventFormView";
    }


    @ExceptionHandler({NumberFormatException.class, NoSuchElementException.class})
    public String handleErrors(HttpServletRequest request, Exception exc, Model model) {
        model.addAttribute("exc", exc.getMessage());
        return "deleteEventFormView";
    }



    @GetMapping("/events")
    public String showEvents(Model model) {
        Iterable<Event> allEvents = eventRepo.findAll();
        model.addAttribute("events", allEvents);
        return "eventsView";
    }

    @GetMapping("/event/{id}")
    public String showEvent(@PathVariable Long id, Model model) {
        Event event = eventRepo.findByIdEvent(id).get();
        String formatDateTime = event.getTerm().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm"));

        model.addAttribute("event", event);
        model.addAttribute("formattedTerm", formatDateTime);
        return "eventView";
    }



}
