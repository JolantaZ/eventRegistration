package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.service.MailService;
import com.gmail.jolantazych.event_register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class EventRegisterController {

    private RegisterService registerService;
    private MailService mailService;

    @Autowired
    public EventRegisterController(RegisterService registerService, MailService mailService) {
        this.registerService = registerService;
        this.mailService = mailService;
    }

    // jak zrobić żeby operacje z tego controllera wykonały się w ramach jednej transakcji? zapis do bazy i mail - wykonają się oba albo żadne
    @PostMapping("/register")
    public String registerOnEvent(@RequestParam Long idEvent, Model model) throws MessagingException {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = registerService.saveUserAndEvent(idEvent, userEmail);
        // user zapisany w bazie na wydrzenie - komunikat o tym? rzuci wyjątkiem z serwisu

        //mailService.sendMail(user); // wyjątek może wystąpić

        return "redirect:/success";  // przekierowanie na stronę successRegister, tam komunikat że registered i dane poszły na maila

    }



    @GetMapping("/success")
    public String goToSuccesPage() {
        return "successRegisterView";  // todo ostylować htmla
    }



    @ExceptionHandler({IllegalStateException.class, MessagingException.class})
    public ModelAndView handleErrors(HttpServletRequest request, Exception exc) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exc.getMessage());  //todo dodać .getMessage ??
        modelAndView.setViewName("errorView");  // todo ostylować komunikat
        return modelAndView;
    }
}
