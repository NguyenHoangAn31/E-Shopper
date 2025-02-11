package com.example.security.controllers.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/account")
public class AccountController {
    @GetMapping()
    public String index() {
        return "dashboard/account/index";
    }
}
