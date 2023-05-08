package com.example.sprintwave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController{

    @GetMapping("/overview")
    public String overview() {
        return "overview";
    }
}