package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.entities.CustomerEntity;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    List<AccountEntity> findByCustomer(CustomerEntity customer);
}
