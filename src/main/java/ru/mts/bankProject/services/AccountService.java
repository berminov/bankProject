package ru.mts.bankProject.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.repos.AccountRepository;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountEntity getAccountById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deposit(int accountId, BigDecimal amount) {
        AccountEntity account = getAccountById(accountId);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account); // Сохранение изменений в базе данных
    }

    @Transactional
    public void withdraw(int accountId, BigDecimal amount) {
        AccountEntity account = getAccountById(accountId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Недостаточно средств!");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account); // Сохранение изменений в базе данных
    }

    @Transactional
    public AccountEntity createNewAccount() {
        AccountEntity account = new AccountEntity();
        account.setBalance(BigDecimal.ZERO);
        accountRepository.save(account);
        return account;
    }
}
