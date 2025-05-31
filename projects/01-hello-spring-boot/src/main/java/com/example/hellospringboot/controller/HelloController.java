package com.example.hellospringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot!";
    }
    
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Boot learning path!";
    }
}