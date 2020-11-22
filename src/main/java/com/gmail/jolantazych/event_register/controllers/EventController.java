package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.model.Event;
import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.repository.EventRepo;
import com.gmail.jolantazych.event_register.repository.UserRepo;
import com.gmail.jolantazych.event_register.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private EventRepo eventRepo;
    private UserRepo userRepo;
    private AuthenticationService authService;

    public EventController(EventRepo eventRepo, UserRepo userRepo, AuthenticationService authService) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.authService = authService;
    }

    @GetMapping("/eventSave")
    public String goToEventSavePage(Model model) {
        model.addAttribute("event", new Event());
        return "saveEventFormView";
    }


    @PostMapping("/eventSave")
    public String saveEvent(@ModelAttribute @Valid Event event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "saveEventFormView";
        }

        Event newEvent = eventRepo.save(event);
        logger.info("New event: {}, {}", newEvent.getIdEvent(), newEvent.getTitle());
        model.addAttribute("successMessage", "Event has been register successfully!");
        return "saveEventFormView";
    }

    // z poziomu admin area
    @GetMapping("/eventDelete")
    public String goToEventDeletePage() {
        return "deleteEventFormView";
    }

    // inicjowany z poziomu widoku eventu
    @GetMapping("/eventDeleteParam/{idEvent}")
    public String goToEventDeletePage(@PathVariable Long idEvent, Model model) {
        model.addAttribute("idEvent", idEvent);
        return "deleteEventFormViewPath";
    }


    //usuwanie z poziomu admin area - podaje id eventu
    //todo II opcja - na liście wydarzeń dla admina pojawia się przycisk delete
    @PostMapping("/eventDelete")
    public String deleteEvent(@RequestParam Long event_id, Model model) {

        Optional<Event> event = eventRepo.findByIdEvent(event_id);
        if (event.isPresent()) {
            List<User> users = event.get().getUsers();
            if (!users.isEmpty()) {
                users.forEach(u -> u.setEvent(null));
                userRepo.saveAll(users);
            }
            eventRepo.deleteById(event_id);
        } else {
            throw new NoSuchElementException("Event with ID: " + event_id + " does not exist");
        }
        model.addAttribute("succesMessage", "Event has been successfully deleted!");
        return "deleteEventFormView";
    }


    @GetMapping("/events")
    public String showEvents(Model model) {
        Iterable<Event> allEvents = eventRepo.findAll();
        model.addAttribute("events", allEvents);
        authService.showLoggedUser(model);
        return "eventsView";
    }


    @GetMapping("/event/{id}")
    public String showEvent(@PathVariable Long id, Model model) {
        Event event = eventRepo.findByIdEvent(id).get();
        String formatDateTime = event.getTerm().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm"));

        authService.showLoggedUser(model);
        model.addAttribute("event", event);
        model.addAttribute("formattedTerm", formatDateTime);
        return "eventView";
    }

    /*
    metoda obsługi wyjątków z przekazaniem komunikatu do widoku, zamiast springowego Whitelabel Error Page
    metoda obsługuje daną klasę kontrolera w którym się znajduje tylko
    Przekazanie wyjątków do widoku, w kótrym wystąpiły
    */
    @ExceptionHandler({NoSuchElementException.class})
    public String handleErrors(HttpServletRequest request, Exception exc, Model model) {
        model.addAttribute("exception", exc.getMessage());
        return "deleteEventFormView";
    }

}
