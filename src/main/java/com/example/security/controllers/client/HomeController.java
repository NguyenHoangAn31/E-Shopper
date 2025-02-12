package com.example.security.controllers.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.security.dto.user.UserRequestCreate;
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

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("pageTitle", "Cart");
        return "client/cart";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            // Đăng nhập bằng username/password
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            System.out.println("Đăng nhập bằng tài khoản: " + username);
            model.addAttribute("user", userService.getUser(username));

        } else if (authentication instanceof OAuth2AuthenticationToken) {
            // Đăng nhập bằng OAuth2
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            String email = oauthUser.getAttribute("email"); // Lấy email

            System.out.println("Đăng nhập bằng OAuth2: " + email + " - " + email);
            model.addAttribute("user", userService.getUser(email));

          
        }

        model.addAttribute("pageTitle", "Profile");
        return "client/profile";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "client/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("pageTitle", "Register");
        model.addAttribute("user", new UserRequestCreate());
        return "client/register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") UserRequestCreate dto, BindingResult bindingResult,
            Model model) {
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
