package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.bankProject.store.entities.AccountEntity;
import ru.mts.bankProject.store.entities.CustomerEntity;

import java.util.List;
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    List<AccountEntity> findByCustomer(CustomerEntity customer);
}
