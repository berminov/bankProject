package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.DepositEntity;

import java.time.LocalDate;
import java.util.List;

public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {
    List<DepositEntity> findByCustomerId(int id);
    List<DepositEntity> findByNextPaymentDate(LocalDate date);
}
