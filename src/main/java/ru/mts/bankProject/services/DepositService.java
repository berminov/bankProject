package ru.mts.bankProject.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.entities.DepositEntity;
import ru.mts.bankProject.store.repos.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepositService {

    private final DepositRepository depositRepository;

    private final CustomerRepository customerRepository;

    private final DepositTypeRepository depositTypeRepository;

    private final InterestPaymentTypeRepository interestPaymentTypeRepository;

    public DepositService(DepositRepository depositRepository, AccountRepository accountRepository, CustomerRepository customerRepository, AccountService accountService, DepositTypeRepository depositTypeRepository, InterestPaymentTypeRepository interestPaymentTypeRepository) {
        this.depositRepository = depositRepository;
        this.customerRepository = customerRepository;
        this.depositTypeRepository = depositTypeRepository;
        this.interestPaymentTypeRepository = interestPaymentTypeRepository;
    }

    public DepositEntity getDepositById(int id) {
        return depositRepository.findById(id).orElse(null);
    }

    public List<DepositEntity> getCustomerDeposits(int id) {
        return depositRepository.findByCustomerId(id);
    }

    public boolean checkBalance(int customerId, BigDecimal amount) {
        AccountEntity account = customerRepository.findById(customerId).get().getBankAccount();
        return account.getBalance().compareTo(amount) >= 0;
    }

    @Transactional
    public void createDeposit(int customerId, BigDecimal amount, int depositTypeId, int period,
                              int interestPaymentTypeId) {

        AccountEntity account = customerRepository.findById(customerId).get().getBankAccount();
        DepositEntity deposit = new DepositEntity();
        deposit.setCustomer(customerRepository.findById(customerId).get());
        deposit.setBankAccount(account);
        deposit.setAmount(amount);
        deposit.setStartDate(LocalDate.now());
        deposit.setEndDate(deposit.getStartDate().plusMonths(period));

        deposit.setDepositType(depositTypeRepository.findById(depositTypeId)
                .orElseThrow(() -> new NoSuchElementException("Deposit type with id " + depositTypeId + " not found")));
        deposit.setInterestPaymentType(interestPaymentTypeRepository.findById(interestPaymentTypeId)
                .orElseThrow(() -> new NoSuchElementException("Interest payment type with id " + interestPaymentTypeId + " not found")));


        deposit.setDepositRate(calculateDepositRate(amount, depositTypeId, period, interestPaymentTypeId));
        account.setBalance(account.getBalance().subtract(amount));

        depositRepository.save(deposit);
    }

    private BigDecimal calculateDepositRate(BigDecimal amount, int depositTypeId, int period,
                                            int interestPaymentTypeId) {
        BigDecimal rate = BigDecimal.ZERO;

        if (amount.compareTo(new BigDecimal("10000")) >= 0 && amount.compareTo(new BigDecimal("100000")) < 0) {
            rate = rate.add(new BigDecimal("1.0"));
        } else if (amount.compareTo(new BigDecimal("100000")) >= 0 && amount.compareTo(new BigDecimal("500000")) < 0) {
            rate = rate.add(new BigDecimal("2.0"));
        } else rate = rate.add(new BigDecimal("3.0"));

        switch (depositTypeId) {
            case 1:
                rate = rate.add(new BigDecimal("1"));
                break;
            case 2:
                rate = rate.add(new BigDecimal("2"));
                break;
            case 3:
                rate = rate.add(new BigDecimal("3"));
                break;
            default:
                rate = rate.add(new BigDecimal("0"));
        }

        if (period == 12) {
            rate = rate.add(new BigDecimal("3"));
        } else if (period == 6) {
            rate = rate.add(new BigDecimal("2"));
        } else {
            rate = rate.add(new BigDecimal("1"));
        }

        switch (interestPaymentTypeId) {
            case 1:
                rate = rate.add(new BigDecimal("2"));
                break;
            case 2:
                rate = rate.add(new BigDecimal("3"));
                break;
            case 3:
                rate = rate.add(new BigDecimal("1"));
                break;
            default:
                rate = rate.add(new BigDecimal("0"));
        }

        return rate;
    }
}
