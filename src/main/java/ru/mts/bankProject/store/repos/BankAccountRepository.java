package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.BankAccountEntity;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Integer> {
}
