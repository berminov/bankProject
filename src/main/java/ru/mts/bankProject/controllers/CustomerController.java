package ru.mts.bankProject.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mts.bankProject.services.CustomerService;
import ru.mts.bankProject.store.entities.CustomerEntity;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public String userInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        String username = userDetails.getUsername();
        CustomerEntity customer = customerService.getCustomerByName(username);
        model.addAttribute("customer", customer);

        return "customer";
    }

}
