package ru.mts.bankProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mts.bankProject.services.DepositService;
import ru.mts.bankProject.store.entities.DepositEntity;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/customers/{customerId}/deposits")
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @GetMapping
    public String customerDeposits(@PathVariable int customerId, Model model) {
        List<DepositEntity> deposits = depositService.getCustomerDeposits(customerId);
        System.out.println(deposits);
        model.addAttribute("deposits", deposits);
        model.addAttribute("customerId", customerId);
        return "depositsList";
    }

    @GetMapping("/{depositId}")
    public String showDepositInfo(@PathVariable("depositId") int id, Model model) {
        DepositEntity deposit = depositService.getDepositById(id);
        model.addAttribute("deposit", deposit);
        return "depositInfo";
    }

    @GetMapping("/open")
    public String showOpenDepositForm(@PathVariable int customerId, Model model) {
        model.addAttribute("customerId", customerId);
        return "openDeposit";
    }

    @PostMapping("/open")
    public String createDeposit(
            @PathVariable int customerId,
            @RequestParam int depositTypeId,
            @RequestParam int period,
            @RequestParam BigDecimal amount,
            @RequestParam int interestPaymentTypeId,
            Model model
    ) {

        if (!depositService.checkBalance(customerId, amount)) {
            model.addAttribute("error", "Недостаточно средств на счете для создания вклада.");
            return "openDeposit";
        }

        depositService.createDeposit(customerId, amount, depositTypeId, period, interestPaymentTypeId);

        return "redirect:/customers/" + customerId + "/deposits";
    }

}
