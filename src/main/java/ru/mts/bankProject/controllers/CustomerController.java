package ru.mts.bankProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/customer")
    public String userInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        // Получаем имя пользователя (или другой идентификатор)
        String username = userDetails.getUsername();

        // Ищем пользователя по имени пользователя
        CustomerEntity customer = customerService.getCustomerByName(username);

        // Передаем информацию о пользователе в модель
        model.addAttribute("customer", customer);

        // Возвращаем имя представления (view)
        return "customer";
    }
}
