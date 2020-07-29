package com.gmail.jolantazych.event_register.controllers;

import com.gmail.jolantazych.event_register.model.User;
import com.gmail.jolantazych.event_register.model.UserDTO;
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
        model.addAttribute("userDTO", new UserDTO()); // binding widoku z modelem
        return "signupFormView";
    }

    @PostMapping("/signup")
    public String registerUser( @ModelAttribute @Valid UserDTO userDTO, BindingResult result, Model model) {

        if (result.hasErrors()) { // pozwala wyświetlić błędy w formularzu, trzeba w htmlu dodać th:error
            return "signupFormView";
        }
        // s = pole obiektu, s1 = kod błędu, s2 = komunikat błędu
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            result.rejectValue("retypePassword", "pass.error", "Password confirmation went wrong!"); // trzeba stworzyć kod w s1 - nigdzie więcej nie wpisuję tej wartości
            return "signupFormView";
        }
        if (userService.isUserAlreadyExists(userDTO.getEmail())) {
            result.rejectValue("email", "email.error", "User with this e-mail already exists!");
            return "signupFormView";
        }

        User newUser = userService.registerWithRoleUser(userDTO);
        model.addAttribute("successMessage", "User has been register succesfully!"); // jeśli przekazuje komunikat do modelu muszę zwracać plik html
        //model.addAttribute("userDTO", new UserDTO());  //to po co?
        System.out.println("New user: " + newUser); // todo zastąpić logiem?
        return "homeView";     // musi być tak bo przekazujemy do widoku komunikat successMessage a NIE tak: "redirect:/home";
    }
}
