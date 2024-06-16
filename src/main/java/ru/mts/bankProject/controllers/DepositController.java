package ru.mts.bankProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mts.bankProject.services.DepositService;
import ru.mts.bankProject.store.entities.CustomerEntity;
import ru.mts.bankProject.store.entities.DepositEntity;

import java.util.List;

@Controller
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @GetMapping("/customers/{customerId}/deposits")
    public String customerDeposits(@PathVariable int customerId, Model model) {
        List<DepositEntity> deposits = depositService.getCustomerDeposits(customerId);
        System.out.println(deposits);
        model.addAttribute("deposits", deposits);
        model.addAttribute("customerId", customerId);
        return "depositsList";
    }

    @GetMapping("/customers/{customerId}/deposits/open")
    public String userInfo(){
        return "openDeposit";
    }
}
