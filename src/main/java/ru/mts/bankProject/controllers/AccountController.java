package ru.mts.bankProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mts.bankProject.services.AccountService;
import ru.mts.bankProject.services.CustomerService;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.entities.CustomerEntity;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{id}")
    public String getAccount(@PathVariable int id, Model model) {
        AccountEntity account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        return "account";
    }

    @PostMapping("/account/{id}/deposit")
    public String deposit(@PathVariable int id, @RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        accountService.deposit(id, amount);
        redirectAttributes.addFlashAttribute("message", "Баланс успешно пополнен!");
        return "redirect:/account/" + id;
    }

    @PostMapping("/account/{id}/withdraw")
    public String withdraw(@PathVariable int id, @RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
        try {
            accountService.withdraw(id, amount);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/account/" + id;
    }

}
