package com.techleads.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping(value = {"/welcome"})
    public String welcome(){
        return "welcome to spring application without security";
    }
}
