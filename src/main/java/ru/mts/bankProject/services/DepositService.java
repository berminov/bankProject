package ru.mts.bankProject.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.entities.CustomerEntity;
import ru.mts.bankProject.store.entities.DepositEntity;
import ru.mts.bankProject.store.repos.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepositService {

    private final DepositRepository depositRepository;

    private final CustomerRepository customerRepository;

    private final DepositTypeRepository depositTypeRepository;

    private final InterestPaymentTypeRepository interestPaymentTypeRepository;

    private final AccountRepository accountRepository;

    public DepositService(DepositRepository depositRepository, AccountRepository accountRepository, CustomerRepository customerRepository, AccountService accountService, DepositTypeRepository depositTypeRepository, InterestPaymentTypeRepository interestPaymentTypeRepository, AccountRepository accountRepository1) {
        this.depositRepository = depositRepository;
        this.customerRepository = customerRepository;
        this.depositTypeRepository = depositTypeRepository;
        this.interestPaymentTypeRepository = interestPaymentTypeRepository;
        this.accountRepository = accountRepository1;
    }

    public int getCustomerIdByUserDetail(UserDetails userDetails){

        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        String username = userDetails.getUsername();

        CustomerEntity customer = customerRepository.findByName(username).orElseThrow(() -> new NullPointerException("No data"));

        return customer.getId();
    }

    public DepositEntity getDepositById(int id) {
        return depositRepository.findById(id).orElse(null);
    }

    public List<DepositEntity> getOpenDepositsByCustomerId(int customerId) {
        return depositRepository.findByCustomerIdAndActiveTrue(customerId);
    }

    public List<DepositEntity> getClosedDepositsByCustomerId(int customerId) {
        return depositRepository.findByCustomerIdAndActiveFalse(customerId);
    }

    public List<DepositEntity> getCustomerDeposits(int id) {
        return depositRepository.findByCustomerId(id);
    }

    public boolean checkBalance(int customerId, BigDecimal amount) {
        AccountEntity account = customerRepository.findById(customerId).get().getBankAccount();
        return account.getBalance().compareTo(amount) >= 0;
    }

    @Transactional
    public void depositToDeposit(int depositId, BigDecimal amount) {
        DepositEntity deposit = getDepositById(depositId);
        AccountEntity account = deposit.getBankAccount();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Недостаточно средств!");
        }
        account.setBalance(account.getBalance().subtract(amount));
        deposit.setAmount(deposit.getAmount().add(amount));
        BigDecimal newDepositRate = calculateDepositRate(deposit.getAmount(), deposit.getDepositType().getId(),
                deposit.getPeriod(), deposit.getInterestPaymentType().getId());
        deposit.setDepositRate(newDepositRate);
        accountRepository.save(account);
        depositRepository.save(deposit);
    }

    @Transactional
    public void withdrawDeposit(int depositId, BigDecimal amount) {
        DepositEntity deposit = getDepositById(depositId);
        AccountEntity account = deposit.getBankAccount();
        if (deposit.getAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Недостаточно средств!");
        }
        deposit.setAmount(deposit.getAmount().subtract(amount));
        account.setBalance(account.getBalance().add(amount));
        BigDecimal newDepositRate = calculateDepositRate(deposit.getAmount(), deposit.getDepositType().getId(),
                deposit.getPeriod(), deposit.getInterestPaymentType().getId());
        deposit.setDepositRate(newDepositRate);
        accountRepository.save(account);
        depositRepository.save(deposit);
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
        deposit.setActive(true);
        deposit.setPeriod(period);
        deposit.setNextPaymentDate(LocalDate.now().plusMonths(1));
        deposit.setPiggyBank(BigDecimal.valueOf(0));
        deposit.setDepositRate(calculateDepositRate(amount, depositTypeId, period, interestPaymentTypeId));
        account.setBalance(account.getBalance().subtract(amount));

        depositRepository.save(deposit);
    }

    @Transactional
    public void closeDeposit(int id) {
        DepositEntity deposit = getDepositById(id);
        deposit.setActive(false);
        AccountEntity account = deposit.getBankAccount();
        account.setBalance(account.getBalance().add(deposit.getAmount()));
        if (deposit.getPiggyBank().compareTo(BigDecimal.ZERO)!=0){
            account.setBalance(account.getBalance().add(deposit.getPiggyBank()));
        }
        depositRepository.save(deposit);
        accountRepository.save(account);
    }

    @Scheduled(cron = "0 0 0 * * *") // Каждый день в полночь
    @Transactional
    public void calculateInterest() {
        LocalDate currentDate = LocalDate.now();

        List<DepositEntity> deposits = depositRepository.findByNextPaymentDate(currentDate);

        for (DepositEntity deposit : deposits) {

            switch (deposit.getInterestPaymentType().getId()) {
                case 1:
                    deposit.getBankAccount().setBalance(deposit.getBankAccount().getBalance().add(getMonthPayment(deposit)));
                    break;
                case 2:
                    deposit.setPiggyBank(deposit.getPiggyBank().add(getMonthPayment(deposit)));
                    break;
                case 3:
                    deposit.setAmount(deposit.getAmount().add(getMonthPayment(deposit)));
                    break;
            }
            if (deposit.getEndDate() == currentDate) {
                closeDeposit(deposit.getId());
            } else deposit.setNextPaymentDate(deposit.getNextPaymentDate().plusMonths(1));

            depositRepository.save(deposit);
        }

    }

    private BigDecimal getMonthRate(DepositEntity deposit) {
        return deposit.getDepositRate().divide(BigDecimal.valueOf(12), 2, RoundingMode.DOWN);
    }

    private BigDecimal getMonthPayment(DepositEntity deposit) {
        return deposit.getAmount().multiply(getMonthRate(deposit).divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN));
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
