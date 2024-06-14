package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
}
