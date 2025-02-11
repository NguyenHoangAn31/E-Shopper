package com.example.security.controllers.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.security.dto.UserCreateDto;
import com.example.security.services.user.UserService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "client/index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("pageTitle", "Shop");
        return "client/shop";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About");
        return "client/about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Contact");
        return "client/contact";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        // truy vấn từ db và hiển thị 
        model.addAttribute("pageTitle", "Profile");
        return "client/profile";
    }

    @GetMapping("/login")
    public String login(Model model) {
        // model.addAttribute("user", new UserRequest());
        model.addAttribute("pageTitle", "Login");
        return "client/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("pageTitle", "Register");
        model.addAttribute("user", new UserCreateDto());
        return "client/register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") UserCreateDto dto, BindingResult bindingResult, Model model) {
        System.out.println("User Details: " + dto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "client/register";
        }

        if (!userService.checkExistAccount(dto.getEmail())) {
            userService.register(dto);
            return "redirect:/login"; 
        }

        model.addAttribute("errorMessage", "Email is already registered!");
        return "client/register"; 
    }
}
