package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private AuthenticationService authService;

    @Autowired
    public AdminController(AuthenticationService authService) {
        this.authService = authService;
    }

    //@Secured("ROLE_ADMIN") // ma być taka sama wartość jak w bazie danych
    @GetMapping("/admin")
    public String goToAdminPage(Model model) {
        authService.showLoggedUser(model);
        return "adminAreaView";
    }


    @GetMapping("/accessDenied")
    public String goToAccessDeniedPage() {
        return "accessDeniedView";
    }

}
