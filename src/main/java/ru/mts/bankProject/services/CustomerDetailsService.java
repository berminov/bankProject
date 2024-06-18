package ru.mts.bankProject.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mts.bankProject.security.CustomerDetails;
import ru.mts.bankProject.store.entities.CustomerEntity;
import ru.mts.bankProject.store.repos.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<CustomerEntity> customer = customerRepository.findByName(s) ;

        if (customer.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new CustomerDetails(customer.get());
    }
}
