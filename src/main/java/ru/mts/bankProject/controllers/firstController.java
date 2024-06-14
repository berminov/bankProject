package ru.mts.bankProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class firstController {

    @GetMapping("/")
    public String first(){
        return "first";
    }
}
