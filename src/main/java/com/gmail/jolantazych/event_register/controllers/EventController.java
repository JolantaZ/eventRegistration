package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.model.Event;
import com.gmail.jolantazych.event_register.repository.EventRepo;
import com.gmail.jolantazych.event_register.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller  // todo endpoint "/event" globalnie dla klasy
public class EventController {

    private EventRepo eventRepo;
    private AuthenticationService authService;

    @Autowired
    public EventController(EventRepo eventRepo, AuthenticationService authService) {
        this.eventRepo = eventRepo;
        this.authService = authService;
    }


    @GetMapping("/eventSave")
    public String goToEventSave(Model model) {
        model.addAttribute("event", new Event()); // łączy obiekt z formularzem - bez tego dane obiektu na czerwono, że błąd
        return "saveEventFormView";
    }

    //todo Price na formularzu domyślnie jako 0,0 - zmienić?
    @PostMapping("/eventSave")
    public String saveEvent(@ModelAttribute @Valid Event event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "saveEventFormView";
        }

        Event newEvent = eventRepo.save(event);
        System.out.println("New event: " + newEvent);  // todo log
        model.addAttribute("successMessage", "Event has been register successfully!");
        return "saveEventFormView";
        // przekierować na stronę z artykułem???

    }

    @GetMapping("/eventDelete")
    public String goToEventDelete() {
        return "deleteEventFormView";
    }

    @GetMapping("/eventDeleteParam/{idEvent}")
    public String goToEventDelete(@PathVariable Long idEvent, Model model) {
        model.addAttribute("idEvent", idEvent);
        return "deleteEventFormViewPath";
    }


    // todo rozwiązać problem usuwania eventu, który jest powiązany z userami - klucz obcy w tabeli user nie pozwala usunąć event
    //zbadać kaskadowość
    // ew. rozwiązanie user.setevent(null)
    // event.delete

    //usuwanie z poziomu admin area - podaje id eventu
    //todo II opcja - na liście wydarzeń dla admina pojawia się przycisk delete
    //@Secured("ROLE_ADMIN")
    @PostMapping("/eventDelete")
    public String deleteEvent(@RequestParam Long event_id, Model model) {

        Optional<Event> event = eventRepo.findByIdEvent(event_id);
        if (event.isPresent()) {
            eventRepo.delete(event.get());
        }
        else {
            throw new NoSuchElementException("Event with ID: " + event_id + " does not exist");
        }
        model.addAttribute("succesMessage", "Event has been successfully deleted!");
        return "deleteEventFormView";
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


    @GetMapping("/events")
    public String showEvents(Model model) {
        Iterable<Event> allEvents = eventRepo.findAll();
        model.addAttribute("events", allEvents);
        authService.showLoggedUser(model);
        return "eventsView";
    }

    // todo  PLN jak wyświetlać?
    @GetMapping("/event/{id}")
    public String showEvent(@PathVariable Long id, Model model) {
        Event event = eventRepo.findByIdEvent(id).get();
        String formatDateTime = event.getTerm().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm"));

        authService.showLoggedUser(model);
        model.addAttribute("event", event);
        model.addAttribute("formattedTerm", formatDateTime);
        return "eventView";
    }


    //    @ExceptionHandler({NumberFormatException.class, NoSuchElementException.class, IllegalStateException.class})
//    public ModelAndView handleErrorss(HttpServletRequest request, Exception exc) {
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception", exc);  //todo dodać .getMessage ??
//
//        if (exc instanceof NumberFormatException || exc instanceof NoSuchElementException) {
//            modelAndView.setViewName("deleteEventFormView");
//        } else if (exc instanceof IllegalStateException) {
//            modelAndView.setViewName("errorView");  // todo ostylować komunikat
//        }
//        return modelAndView;
//    }

}
