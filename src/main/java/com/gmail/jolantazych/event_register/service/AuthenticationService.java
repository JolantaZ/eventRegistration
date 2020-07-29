package com.gmail.jolantazych.event_register.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthenticationService {

    // pobiera wyrażenie będące loginem do strony
    public String getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public boolean isLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser");
    }

    //metoda używana przez menu, decyduje jakie elementy są widoczne i pokazuje kto jest zalogowany
    public Model showLoggedUser(Model model) {
        model.addAttribute("isLogged", isLogged());
        if (isLogged()) {
            model.addAttribute("email", getUserLogin());
        }
        return model;
    }
}
