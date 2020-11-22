package com.gmail.jolantazych.event_register.service;

import com.gmail.jolantazych.event_register.model.Event;
import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.repository.EventRepo;
import com.gmail.jolantazych.event_register.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

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
        if(!isUserAlreadyRegisteredForEvent(user)) {
            user.setEvent(event);
            userRepo.save(user); // exception o validacji retypepassword
        }
        else {
            throw new IllegalStateException("User can be registered for 1 event at a time!");
        }
        return user;
    }

    public boolean isUserAlreadyRegisteredForEvent(User user) {
        Event eventByUserID = eventRepo.findEventByUserID(user.getIdUser());
        return eventByUserID != null;
    }


}
