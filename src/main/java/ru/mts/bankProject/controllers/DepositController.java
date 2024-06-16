package ru.mts.bankProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mts.bankProject.services.DepositService;
import ru.mts.bankProject.store.entities.DepositEntity;

import java.math.BigDecimal;
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
    public String showOpenDepositForm(@PathVariable int customerId, Model model) {
        model.addAttribute("customerId", customerId);
        return "openDeposit";
    }

    @PostMapping("/customers/{customerId}/deposits/open")
    public String createDeposit(
            @PathVariable int customerId,
            @RequestParam String depositType,
            @RequestParam int period,
            @RequestParam BigDecimal amount,
            @RequestParam String interestPaymentType,
//            @RequestParam boolean percentPerMonth,
//            @RequestParam boolean percentOneTime,
//            @RequestParam boolean capitalization,
            Model model
    ) {

        boolean canRefill = false;
        boolean canWithdraw = false;
        switch (depositType){
            case "refillAndWithdrawal":
                canRefill = true;
                canWithdraw = true;
                break;
            case "onlyRefill":
                canRefill = true;
                break;
            default:
                break;
        }

        if (!depositService.checkBalance(customerId, amount)) {
            model.addAttribute("error", "Недостаточно средств на счете для создания вклада.");
            return "openDeposit";
        }

        BigDecimal depositRate = BigDecimal.valueOf(10.00);

        depositService.createDeposit(customerId,  amount, depositType, period, interestPaymentType );

        return "redirect:/customers/" + customerId + "/deposits";
    }

}
