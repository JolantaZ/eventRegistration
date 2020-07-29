package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeContactController {

    private AuthenticationService authService;

    @Autowired
    public HomeContactController(AuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/home")
    public String goToHomePage(Model model) {
        authService.showLoggedUser(model);
        return "homeView";
    }

    @GetMapping("/contact")
    public String goToContactPage(Model model) {
      authService.showLoggedUser(model);
        return "contactView";
    }





}
