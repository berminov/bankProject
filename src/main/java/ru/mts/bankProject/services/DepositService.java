package ru.mts.bankProject.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.entities.DepositEntity;
import ru.mts.bankProject.store.repos.AccountRepository;
import ru.mts.bankProject.store.repos.CustomerRepository;
import ru.mts.bankProject.store.repos.DepositRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DepositService {

    private final DepositRepository depositRepository;

    private final CustomerRepository customerRepository;

    private final AccountService accountService;

    public DepositService(DepositRepository depositRepository, AccountRepository accountRepository, CustomerRepository customerRepository, AccountService accountService) {
        this.depositRepository = depositRepository;
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }

    public DepositEntity getDepositById(int id){
        return depositRepository.findById(id).orElse(null);
    }

    public List<DepositEntity> getCustomerDeposits(int id){
        return depositRepository.findByCustomerId(id);
    }

    public boolean checkBalance (int customerId, BigDecimal amount){
        AccountEntity account = customerRepository.findById(customerId).get().getBankAccount();
        return account.getBalance().compareTo(amount) >= 0;
    }
    @Transactional
    public void createDeposit(int customerId, BigDecimal amount, String depositType, int period, String interestPaymentType){

        AccountEntity account = customerRepository.findById(customerId).get().getBankAccount();
        DepositEntity deposit = new DepositEntity();
        deposit.setCustomer(customerRepository.findById(customerId).get());
        deposit.setBankAccount(account);
        deposit.setAmount(amount);
//        deposit.setCanRefill(canRefill);
//        deposit.setCanWithdraw(canWithdraw);
        deposit.setStartDate(LocalDate.now());
        deposit.setEndDate(deposit.getStartDate().plusMonths(period));
//        deposit.setDepositRate(depositRate);
//        deposit.setCapitalization(capitalization);
//        deposit.setPercentPerMonth(percentPerMonth);
//        deposit.setPercentOneTime(percentOnetime);
        account.setBalance(account.getBalance().subtract(amount));
        depositRepository.save(deposit);
    }
}
