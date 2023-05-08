package com.example.sprintwave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getHomepage()
    {
        return "frontpage";
    }

    @GetMapping("/signup")
    public String getSignupPage()
    {
        return "signup";
    }
}
