package com.example.dweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/backs")
    public String backs() {
        return "backs"; 
    }

    @GetMapping("/news")
    public String news() {
        return "news"; 
    }

    @GetMapping("/information")
    public String information() {
        return "information"; 
    }

    @GetMapping("/downloads")
    public String uploadinfo() {
        return "uploadinfo"; 
    }
}
