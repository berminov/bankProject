package ru.mts.bankProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mts.bankProject.services.DepositService;
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
        return "depositsList";
    }

//    @GetMapping("/deposit/{id}")
//    public String depositDetails(@PathVariable Long id, Model model) {
//        DepositEntity deposit = depositService.getDepositById(id);
//        model.addAttribute("deposit", deposit);
//        return "deposit";
//    }

//    @PostMapping("/account/openDeposit")
//    public String openDeposit(@RequestParam Long accountId, @RequestParam String depositNumber, @RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
//        try {
//            depositService.openDeposit(accountId, depositNumber, amount);
//            redirectAttributes.addFlashAttribute("message", "Вклад успешно открыт");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/account/" + accountId;
//    }
}
