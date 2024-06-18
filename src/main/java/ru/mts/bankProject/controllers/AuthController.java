package ru.mts.bankProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mts.bankProject.services.CustomerService;
import ru.mts.bankProject.store.entities.CustomerEntity;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("customer") CustomerEntity customer) {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String registrateCustomer(@ModelAttribute("customer") CustomerEntity customer) {
        customerService.register(customer);
        return "redirect: /auth/login";
    }
}
