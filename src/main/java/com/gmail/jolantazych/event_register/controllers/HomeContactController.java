package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeContactController {

    private AuthenticationService authService;

    public HomeContactController(AuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/home")
    public String goToHomePage(Model model) {
        model.addAttribute("isLogged", authService.isLogged());
        if (authService.isLogged()) {
            model.addAttribute("email", authService.getUserLogin());
        }
        return "homeView";
    }

    @GetMapping("/contact")
    public String goToContactPage(Model model) {
        model.addAttribute("isLogged", authService.isLogged());
        if (authService.isLogged()) {
            model.addAttribute("email", authService.getUserLogin());}
        return "contactView";
    }





}
