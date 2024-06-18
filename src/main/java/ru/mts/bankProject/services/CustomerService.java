package ru.mts.bankProject.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.entities.CustomerEntity;
import ru.mts.bankProject.store.repos.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final AccountService accountService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerService(CustomerRepository customerRepository, AccountService accountService, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder1) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
    }

    public CustomerEntity getCustomerById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerEntity getCustomerByName(String name) {
        return customerRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void register(CustomerEntity customer) {
        AccountEntity account = accountService.createNewAccount();
        customer.setBankAccount(account);

        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customerRepository.save(customer);

    }
}


