package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignupController {

    private UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String goToSigninPage(Model model) {
        model.addAttribute("user", new User());
        return "signupFormView";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "signupFormView";
        }

        if (!user.getPassword().equals(user.getRetypePassword())) {
            result.rejectValue("retypePassword", "pass.error", "Password confirmation went wrong!");
            return "signupFormView";
        }
        if (userService.isUserAlreadyExists(user.getEmail())) {
            result.rejectValue("email", "email.error", "User with this e-mail already exists!");
            return "signupFormView";
        }

        User newUser = userService.registerWithRoleUser(user);
        model.addAttribute("successMessage", "User has been register succesfully!");

        System.out.println("New user: " + newUser);
        return "homeView";
    }
}
