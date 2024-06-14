package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.DepositEntity;

public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {
}
