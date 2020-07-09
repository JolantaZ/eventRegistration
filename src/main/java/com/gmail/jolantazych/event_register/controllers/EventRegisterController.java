package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.service.MailService;
import com.gmail.jolantazych.event_register.service.RegisterService;
import com.gmail.jolantazych.event_register.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class EventRegisterController {

    private RegisterService registerService;
    private MailService mailService;

    public EventRegisterController(RegisterService registerService, MailService mailService) {
        this.registerService = registerService;
        this.mailService = mailService;
    }

    private static final String MAIL_SUBJECT = "Confirmation of registration for the event";


    // todo poprawić implementację
    @GetMapping("/register/{idEvent}")
    public String registerOnEvent(@PathVariable Long idEvent, Model model) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = registerService.saveUserAndEvent(idEvent, userEmail);


        String completedEmailContent = registerService.completeEmailContent(user);
        try {
            mailService.sendMail(userEmail, MAIL_SUBJECT, completedEmailContent);
        } catch (MessagingException e) {
            return "Error occureed during sending e-mail";
        }


        return "redirect:/success";


    }



    @GetMapping("/success")
    public String goToSuccesPage() {
        return "successRegisterView";
    }



    @ExceptionHandler({IllegalStateException.class})
    public ModelAndView handleErrorss(HttpServletRequest request, Exception exc) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exc.getMessage());
        modelAndView.setViewName("errorView");
        return modelAndView;

    }




}
