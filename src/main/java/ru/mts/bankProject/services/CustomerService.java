package ru.mts.bankProject.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.entities.CustomerEntity;
import ru.mts.bankProject.store.repos.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final AccountService accountService;

    public CustomerService(CustomerRepository customerRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }

    public CustomerEntity getCustomerById(int id){
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerEntity getCustomerByName(String name){
        return customerRepository.findByName(name).orElse(null);
    }

    @Transactional
    public void register(CustomerEntity customer){
        AccountEntity account = accountService.createNewAccount();
        customer.setBankAccount(account);
        customerRepository.save(customer);

    }
}


