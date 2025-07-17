package com.example.nic_login_registration.controller;


import com.example.nic_login_registration.model.User;
import com.example.nic_login_registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String welcomeHome() {
        return "home"; // home.html under src/main/resources/templates/
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Optionally, check if username already exists
        if (userService.getByUsername(user.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("error", "User already exists!");
            return "redirect:/register";
        }
        userService.register(user);
        redirectAttributes.addFlashAttribute("success", "Registered successfully! You can now login.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/userWelcome")
    public String welcomePage(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", principal.getName());
        return "user-welcome";
    }

}




