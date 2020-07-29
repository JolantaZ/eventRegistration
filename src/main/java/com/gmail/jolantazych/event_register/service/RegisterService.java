package com.gmail.jolantazych.event_register.service;

import com.gmail.jolantazych.event_register.model.Event;
import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.repository.EventRepo;
import com.gmail.jolantazych.event_register.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IWebContext;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class RegisterService {

    final static String BANK_ACCOUNT = "93 2490 0005 8077 2308 5460 5439";

    private UserRepo userRepo;
    private EventRepo eventRepo;
    private TemplateEngine templateEngine;

    @Autowired
    public RegisterService(UserRepo userRepo, EventRepo eventRepo, TemplateEngine templateEngine) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.templateEngine = templateEngine;
    }


    public User saveUserAndEvent(Long eventID, String email) {
        User user = userRepo.findByEmail(email);
        Event event = eventRepo.findByIdEvent(eventID).get();
        if(!isUserRegisterForEvent(user)) {
            user.setEvent(event);
            userRepo.save(user); // exception o validacji retypepassword
        }
        else {
            throw new IllegalStateException("User can be register for 1 event at a time!");
        }
        return user;
    }

    public boolean isUserRegisterForEvent(User user) {
        Event eventByUserID = eventRepo.findEventByUserID(user.getIdUser());
        return eventByUserID != null;
    }


    public String completeEmailContent(User user) {

        Event event = user.getEvent();

        Context context = new Context();

        context.setVariable("header", "Event registration confirmation");
        context.setVariable("email", user.getEmail());
        context.setVariable("userName", user.getName());
        context.setVariable("eventTitle", event.getTitle());
        context.setVariable("eventTerm", event.getTerm().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // ??? będzie ok?
        context.setVariable("eventPrice", BigDecimal.valueOf(event.getPrice()).toString());
        context.setVariable("bankAccount", BANK_ACCOUNT);

        String mailView = templateEngine.process("mailView", context);
        return mailView;
    }


}
