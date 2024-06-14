package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.DepositTypeEntity;

public interface DepositTypeRepository extends JpaRepository<DepositTypeEntity, Integer> {
}
