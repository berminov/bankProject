package ru.mts.bankProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mts.bankProject.services.CustomerService;
import ru.mts.bankProject.store.entities.CustomerEntity;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer/{id}")
    public String userInfo(@PathVariable int id, Model model){
        CustomerEntity customer = customerService.getCustomerById(id);
        System.out.println(customer);
        model.addAttribute("customer", customer);

        return "customer";
    }
}
