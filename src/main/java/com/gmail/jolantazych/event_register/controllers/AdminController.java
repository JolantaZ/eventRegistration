package com.gmail.jolantazych.event_register.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String goToAdminPage() {
        return "adminAreaView";
    }

}
