package ru.mts.bankProject.services;

import org.springframework.stereotype.Service;
import ru.mts.bankProject.store.entities.CustomerEntity;
import ru.mts.bankProject.store.repos.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity getCustomerById(int id){
        return customerRepository.findById(id).orElse(null);
    }

    public CustomerEntity getCustomerByName(String name){
        return customerRepository.findByName(name).orElse(null);
    }
}
